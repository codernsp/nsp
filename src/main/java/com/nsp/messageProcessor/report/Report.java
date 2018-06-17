package com.nsp.messageProcessor.report;

import com.nsp.messageProcessor.product.ProductType;
import com.nsp.messageProcessor.sale.Sale;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Class generates reports for every 10th and 50th sale
 * Message and Sale have 1:1 mapping, hence tracks count of sale
 */
public class Report implements Reportable {

    private Sale sale ;
    //List of sales
    private static List<Sale> globalSaleList;

    //Map of sale
    private static Map<ProductType, List<Sale>> saleMap;

    //Report on sale
    @Autowired
    public Report(Sale sale) {
        setSale(sale);
        on10thSale();
        on5othSale();
    }

    /**
     * Report on every 50th Sale
     */
    @Override
    public void on5othSale() {
        if (getSaleCount() % 50 == 0) {
            System.out.println("\n*******Application is pausing. A report of adjustments per sale will now be logged.********");
            Set<ProductType> productTypeSet = new HashSet<>();
            for (Sale sale : globalSaleList) {
                productTypeSet.add(sale.getProduct().getProductType());
            }

            for (ProductType productType : productTypeSet) {
                if (Sale.getAdjustmentMap().containsKey(productType)) {
                    System.out.println("\nProduct type " + ": " + productType + " has total " + Sale.getAdjustmentMap().get(productType).size()
                            + " adjustments\n" + Sale.getAdjustmentMap().get(productType));
                }
            }
        }
    }

    /**
     * Report on every 10th sale
     */
    @Override
    public void on10thSale() {
        if (getSaleCount() % 10 == 0) {
            System.out.println( "\nReport of total sales per product type at sale count : " + getSaleCount());
            //Print details per product
            for(Map.Entry entry : getSaleMap().entrySet()){
                ProductType type  = (ProductType) entry.getKey();
                System.out.println("Product " + type + " has total " + ((List<Sale>)entry.getValue()).size()
                        + " sales." + " Total Value for the sale is :  £" + Sale.getSaleMap().get(type)/100);
            }
        }
    }

    /**
     * sets the sale in saleList
     * @param sale
     */
    @Override
    public void setSale(Sale sale) {
        List<Sale>saleList = new ArrayList<>();
        if (getSaleMap() == null){
            saleMap = new HashMap<>();
        }
        if(globalSaleList == null){
            globalSaleList = new ArrayList<>();
        }

        ProductType productType = sale.getProduct().getProductType();

        //product exists add to existing list
        if(getSaleMap().containsKey(productType)) {
            saleList = getSaleMap().get(productType);
        }
        else {
            saleList = new ArrayList<>();
        }
        saleList.add(sale);
        globalSaleList.add(sale);
        addToSaleMap(productType, saleList);
    }

    /**
     * tracks sale
     * @param type
     * @param saleList
     */
    @Override
    public void addToSaleMap(ProductType type, List<Sale> saleList){

        saleMap.put(type, saleList);
    }

    /**
     *
     * Returns saleMap
     * @return
     */
    @Override
    public Map<ProductType, List<Sale>> getSaleMap(){
        if(saleMap == null){
            saleMap = new HashMap<>();
        }
        return saleMap;
    }
    /**
     *
     * @return number of sales
     */
    @Override
    public int getSaleCount() {
        if (globalSaleList != null)
            return globalSaleList.size();

        return 0;
    }

    /**
     *
     *
     * @return saleList
     */
    public static List<Sale> getGlobalSaleList() {
        return globalSaleList;
    }
}
