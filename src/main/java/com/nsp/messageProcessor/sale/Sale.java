/******************************************************************
 * @author: npandey
 * @Date: 09/06/18
 * Sale.java
 ******************************************************************/
package com.nsp.messageProcessor.sale;

import com.nsp.messageProcessor.adjustment.Adjustment;
import com.nsp.messageProcessor.adjustment.AdjustmentType;
import com.nsp.messageProcessor.message.Type;
import com.nsp.messageProcessor.product.Product;
import com.nsp.messageProcessor.product.ProductType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class captures the sale for each product
 */
public class Sale implements Saleable {

    //quantity of sale
    private final int numOfSale;

    @Autowired
    //Product
    private final Product product;

    @Autowired
    //type of adjustment
    private final AdjustmentType adjustment;

    //SaleMap
    private static Map<ProductType, Double> saleMap;

    //Adjustment Map
    private static Map<ProductType, List<Adjustment>> adjustmentMap;

    //Type of Sale
    private final Type type;

    /**
     * @return numOfSale
     */
    public int getNumOfSale() {
        return numOfSale;
    }

    /**
     * @return message
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return AdjusmentType
     */
    public AdjustmentType getAdjustment() {
        return adjustment;
    }

    /**
     * @return Type of sale
     */
    public Type getType() {
        return type;
    }

    /**
     * @return saleMap
     */
    public static Map<ProductType, Double> getSaleMap() {
        return saleMap;
    }

    /**
     * Logs message on sale
     */
    @Override
    public void onSale() {
        for (Map.Entry entry : getSaleMap().entrySet()) {
            System.out.println(entry.getKey() + " has sale of " + entry.getValue());
        }
    }

    /**
     * Returns sale amount from saleMap
     *
     * @param productType
     * @return Double sale total
     */
    @Override
    public Double getSale(ProductType productType) {
        if (getSaleMap().keySet().contains(productType)) {
            System.out.println(productType + " has sale of " + getSaleMap().get(productType));
            return getSaleMap().get(productType);
        }
        return null;
    }


    /**
     * captures sale in map
     *
     * @param prod
     */
    private void captureSale(Product prod) {
        if (saleMap == null)
            saleMap = new HashMap<>();

        double newPrice = calculateNewPrice(prod);

        saleMap.put(prod.getProductType(), newPrice);
    }

    /**
     * Calculates Sale of Product
     *
     * @param product
     * @return
     */
    private double calculateNewPrice(Product product) {
        double originalPrice = 0;
        if (getSaleMap().keySet().contains(product.getProductType())) {
            originalPrice = getSaleMap().get(product.getProductType());
        }

        double newPrice = getNewPrice(product, originalPrice);

        if (newPrice > 0) {
            saleMap.put(product.getProductType(), newPrice);
        }
        return newPrice;
    }

    /**
     * helps to calculate new sale price of product
     *
     * @param product
     * @param originalPrice
     * @return
     */
    private double getNewPrice(Product product, double originalPrice) {
        if (type.equals(Type.SIMPLE_SALE)) {
            return originalPrice + product.getPrice();
        }
        if (getType().equals(Type.DETAIL_SALE)) {
            return originalPrice + (product.getPrice() * getNumOfSale());
        }
        if (getType().equals(Type.ADJUSTMENT_SALE)) {//add to overall sale
            //add to adjustment list
            addToAdjustments(product);

            Double x = getAdjustmentPrice(originalPrice);
            if (x != null) return x;
        }
        return originalPrice;
    }

    /**
     * calculates adjustments
     *
     * @param originalPrice
     * @return
     */
    private Double getAdjustmentPrice(double originalPrice) {
        if (getAdjustment().equals(AdjustmentType.MULTIPLY))
            return originalPrice * product.getPrice();
        if (getAdjustment().equals(AdjustmentType.ADD))
            return originalPrice + product.getPrice();
        if (getAdjustment().equals(AdjustmentType.ADD))
            return originalPrice - product.getPrice();
        return null;
    }

    /**
     * Adds adjustments
     *
     * @param product
     */
    private void addToAdjustments(Product product) {
        List adjustmentList = null;
        if (adjustmentMap == null)
            adjustmentMap = new HashMap<>();

        Adjustment adjustment = new Adjustment();
        adjustment.setAdjustmentAmount(product.getPrice()
        );
        //default to ADD
        if(getAdjustment() ==  null){
            adjustment.setAdjustmentType(AdjustmentType.ADD);
        }
        adjustment.setAdjustmentType(getAdjustment());
        if (adjustmentMap.keySet().contains(product.getProductType())) {
            adjustmentList = adjustmentMap.get(product.getProductType());
        } else {
            adjustmentList = new ArrayList();
        }
        adjustmentList.add(adjustment);
        adjustmentMap.put(product.getProductType(), adjustmentList);
    }

    /**
     * private Constuctor
     *
     * @param builder
     */
    @Autowired
    private Sale(Builder builder) {
        this.numOfSale = builder.numOfSale;
        this.adjustment = builder.adjustment;
        this.product = builder.product;
        this.type = builder.type;
        captureSale(product);
    }

    /**
     * @return adjustmentMap
     */
    public static Map<ProductType, List<Adjustment>> getAdjustmentMap() {
        return adjustmentMap;
    }

    /**
     * Inner Builder class
     */
    public static class Builder {
        //quantity of sale
        private int numOfSale;

        //product
        private Product product;

        //type of sale
        private Type type;

        //type of adjustment
        private AdjustmentType adjustment;

        public Builder setNumOfSale(final int numOfSale) {
            this.numOfSale = numOfSale;
            return this;
        }

        public Builder setProduct(final Product product) {
            this.product = product;
            return this;
        }

        public Builder setAdjustment(final AdjustmentType adjustment) {
            this.adjustment = adjustment;
            return this;
        }


        public Builder setType(final Type type) {
            this.type = type;
            return this;
        }

        /**
         * Builds and returns Sale
         *
         * @return
         */
        public Sale build() {
            return new Sale(this);
        }
    }
}
