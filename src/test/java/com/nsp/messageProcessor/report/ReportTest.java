package com.nsp.messageProcessor.report;


import com.nsp.messageProcessor.message.Type;
import com.nsp.messageProcessor.product.Product;
import com.nsp.messageProcessor.product.ProductType;
import com.nsp.messageProcessor.sale.Sale;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportTest {

    Product product;
    Sale sale;
    Sale sale2;
    Report report;


    @Before
    public void setUp() throws Exception {

        //Product
         product = new Product.Builder().
                        setProductType(ProductType.MANGO).
                        setPrice(500).
                        build();

         //Sale
        sale = new Sale.Builder().
                    setNumOfSale(5).
                    setProduct(product).
                    setType(Type.SIMPLE_SALE).
                    build();

        sale2 = new Sale.Builder().
                setNumOfSale(7).
                setProduct(product).
                setType(Type.SIMPLE_SALE).
                build();

        report = new Report(sale);
        report.setSale(sale2);
        sale = new Sale.Builder().
                setNumOfSale(2).
                setProduct(product).
                setType(Type.SIMPLE_SALE).
                build();
        report.setSale(sale);
    }

    @Test
    public void testReportSaleMap(){
        assert(report.getSaleCount() == 3);
        assert(report.getSaleMap().containsKey(ProductType.MANGO));
    }



}
