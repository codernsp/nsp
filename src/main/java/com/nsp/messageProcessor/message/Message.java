package com.nsp.messageProcessor.message;

import com.nsp.messageProcessor.product.Product;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.Map;

/**
 * This class holds the Message using Builder pattern
 */
public class Message {

  @Autowired
  //product
  private final Product product;


  //sale
  private final double qty;

  //product message map
  public Map<Product, Message> productMessageMap ;

  /**
   * private constructor
   * @param builder
   */
  @Autowired
  private Message(Builder builder) {
    this.product = builder.product;
    this.qty = builder.qty;
    createProdMessageMap(product);
  }

  /**
   * Maintain map of product and message
   * @param product
   * @return
   */
  private void createProdMessageMap(Product product){
    if (productMessageMap == null){
      productMessageMap = new HashMap<Product, Message>();
    }
    productMessageMap.put(product, this);

  }

  /**
   *
   * @return productMessageMap
   */
  public Map<Product, Message> getProdMessageMap(){
    return productMessageMap;
  }

  /**
   *
   * @return Product
   */
  public Product getProduct() {
    return product;
  }

  /**
   *
   * @return double
   */
  public double getQty() {
    return qty;
  }

  /******************************************************************
   * This is the Builder class which builds and returns Message
   ******************************************************************/
  public static class Builder{
    //product
    private Product product;

    //sale
    private double qty;

    /**
     * sets product
     * @param product
     * @return Builder
     */
    public Builder setProduct(final Product product) {
      this.product = product;
      return this;
    }

    /**
     * sets sale
     * @param sale
     * @return
     */
    public Builder setQty(final double sale) {
      this.qty = qty;
      return this;
    }

    /**
     * Returns instance of Messsage
     *
     * @return
     */
    public Message build(){
      return new Message(this);
    }
  }
}
