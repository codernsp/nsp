package com.nsp.messageProcessor.sale;


import com.nsp.messageProcessor.product.ProductType;


/**
 * Interface for Sale
 */
public interface Saleable {
    //on Sale method
    void onSale();

    //get sale on product
    Double getSale(ProductType productType);

}
