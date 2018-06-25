
package com.nsp.messageProcessor.product;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class holds the Product
 */
public class Product {

    @Autowired
    //ProductType
    private final ProductType productType;

    //price of product
    //Assuming price is always in pence
    private final double price;

    /**
     * private Constructor
     * @param builder
     */
    @Autowired
    private Product(Builder builder){
        this.productType = builder.productType;
        this.price = builder.price;
    }

    /**
     *
     * returns ProductType
     * @return
     */
    public ProductType getProductType() {
        return productType;
    }

    /**
     * returns price
     * @return
     */
    public double getPrice() {
        return price;
    }

    public static class Builder{
        //ProductType
        private ProductType productType;

        //price of product
        //Assuming price is always in pence
        private double price;

        /**
         * sets productType
         * @param productType
         * @return
         */
        public Builder setProductType(final ProductType productType) {
            this.productType = productType;
            return this;
        }

        /**
         * sets price
         * @param price
         * @return
         */
        public Builder setPrice(final double price) {
            this.price = price;
            return this;
        }

        /**
         * builds and returns Product
         *
         * @return
         */
        public Product build(){
            return new Product(this);
        }
    }
}
