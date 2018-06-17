package com.nsp.messageProcessor.report;

import com.nsp.messageProcessor.product.ProductType;
import com.nsp.messageProcessor.sale.Sale;

import java.util.List;
import java.util.Map;

/**
 * Interface to Report
 */
public interface Reportable {

    //reports after every 50th sale
    void on5othSale();

    //reports after every 10th sale
    void on10thSale();

    //sets sale
    void setSale(Sale sale);

    //tracks sale per product type
    void addToSaleMap(ProductType type, List<Sale> saleList);

    //returns number of sales
    int getSaleCount();

    //saleMap
    Map<ProductType, List<Sale>> getSaleMap();
}
