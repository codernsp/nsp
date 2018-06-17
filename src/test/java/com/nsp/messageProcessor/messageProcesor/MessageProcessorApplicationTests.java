package com.nsp.messageProcessor.messageProcesor;

import com.nsp.messageProcessor.adjustment.AdjustmentType;
import com.nsp.messageProcessor.message.Message;
import com.nsp.messageProcessor.message.Type;
import com.nsp.messageProcessor.product.Product;
import com.nsp.messageProcessor.product.ProductType;
import com.nsp.messageProcessor.report.Report;
import com.nsp.messageProcessor.sale.Sale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;


@RunWith(JUnit4.class)

public class MessageProcessorApplicationTests {


    private Product appleProd1;
    private Product appleProd2;
    private Product appleProd3;
    private Product bananaProd1;
    private Product bananaProd2;
    private Product bananaProd3;
    private Product avocadoProd;
    private Product grapeProd;
    private Product mangoProd1;



    @Before
    public void onSetUp() throws Exception {
        appleProd1 = new Product.Builder().
                setProductType(ProductType.APPLE).
                setPrice(30).build();
        appleProd2 = new Product.Builder().
                setProductType(ProductType.APPLE).
                setPrice(5).build();
        appleProd3 = new Product.Builder().
                setProductType(ProductType.APPLE).
                setPrice(15).build();
        bananaProd1 = new Product.Builder().
                setProductType(ProductType.BANANA).
                setPrice(25).build();
        bananaProd2 = new Product.Builder().
                setProductType(ProductType.BANANA).
                setPrice(27).build();
        bananaProd3 = new Product.Builder().
                setProductType(ProductType.BANANA).
                setPrice(25).build();
        avocadoProd = new Product.Builder().
                setProductType(ProductType.AVOCADO).
                setPrice(15).build();
        grapeProd = new Product.Builder().
                setProductType(ProductType.GRAPE).
                setPrice(275).build();
        mangoProd1 = new Product.Builder().
                setProductType(ProductType.MANGO).
                setPrice(30).build();
    }


    private Message prepareSimpleMessage(Product product) throws Exception {
        //type 1 - simple sale message e.g. apple @ 10p ( assume quantity is 1 for sale)
        Message message1 = new Message.Builder().
                setProduct(product).
                build();

        return message1;

    }

    private Message prepareDetailedMessage(Product product) {
        //type 1 - simple sale message e.g. apple @ 10p ( assume quantity is 1 for sale)
        Message message1 = new Message.Builder().
                setProduct(product).
                setQty(10).
                build();

        return message1;

    }

    /**
     * Test simple message
     */
    @Test
    public void processSimpleMessage() throws Exception {
        prepareSimpleMessage(appleProd1);
        //sale with simple message
        Sale sale1 = new Sale.Builder().
                setNumOfSale(30).
                setType(Type.SIMPLE_SALE).
                setProduct(appleProd1).
                build();
        assert (30 == sale1.getSale(appleProd1.getProductType()));
        Sale.getSaleMap().clear();
        Sale.getAdjustmentMap().clear();
    }

    /**
     * Test Detailed message calculations and processing
     */
    @Test
    public void processDetailedMessage() throws Exception {
        prepareDetailedMessage(appleProd2);
        Sale sale1 = new Sale.Builder().
                setType(Type.DETAIL_SALE).
                setNumOfSale(20).
                setProduct(appleProd2).
                build();

        //sale with simple message

        assertEquals(100, sale1.getSale(appleProd2.getProductType()), 0);
        Sale.getSaleMap().clear();
        Sale.getAdjustmentMap().clear();
    }

    /**
     * Test adjustment message
     */
    @Test
    public void processAdjustmentMessage() throws Exception {
        prepareDetailedMessage(appleProd2);
        Sale sale1 = new Sale.Builder().
                setType(Type.DETAIL_SALE).
                setNumOfSale(20).
                setProduct(appleProd2).
                build();

        //sale with simple message

        assertEquals(100, sale1.getSale(appleProd2.getProductType()), 0);

        Sale.getSaleMap().clear();
        Sale.getAdjustmentMap().clear();
    }

    /**
     * Test processing of simple sales
     */
    @Test
    public void processSimpleSales() throws Exception {
        Sale sale1 = new Sale.Builder().
                    setNumOfSale(5).
                    setProduct(bananaProd1).
                    setType(Type.SIMPLE_SALE)
                    .build();

        Sale sale2 = new Sale.Builder().
                            setNumOfSale(2).
                            setProduct(bananaProd3).
                            setType(Type.SIMPLE_SALE)
                            .build() ;

        Sale sale3 = new Sale.Builder().
                setNumOfSale(5).
                setProduct(avocadoProd).
                setType(Type.SIMPLE_SALE)

                .build();

        Sale sale4 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(grapeProd).
                setType(Type.SIMPLE_SALE)
                .build();

        Report report = new Report(sale1);
        report.setSale(sale2);
        report.setSale(sale3);
        report.setSale(sale4);
        assert(report.getSaleCount() == 4);
        assert(report.getSaleMap().containsKey(ProductType.BANANA));
        assert(report.getSaleMap().get(ProductType.BANANA).size() == 2 );
        assert(report.getSaleMap().containsKey(ProductType.AVOCADO));
        assert(report.getSaleMap().get(ProductType.AVOCADO).size() == 1 );
        assert(report.getSaleMap().get(ProductType.GRAPE).size() == 1 );
        report.getSaleMap().clear();
        report.getGlobalSaleList().clear();
        Sale.getSaleMap().clear();
        Sale.getAdjustmentMap().clear();
    }

    /**
     * Test processing of detailed sales
     */
    @Test
    public void processDetailedSales() throws Exception {
        Sale sale1 = new Sale.Builder().
                setNumOfSale(5).
                setProduct(bananaProd1).
                setType(Type.DETAIL_SALE)
                .build();

        Sale sale2 = new Sale.Builder().
                setNumOfSale(2).
                setProduct(bananaProd3).
                setType(Type.DETAIL_SALE)
                .build() ;

        Sale sale3 = new Sale.Builder().
                setNumOfSale(5).
                setProduct(avocadoProd).
                setType(Type.DETAIL_SALE)
                .build();

        Sale sale4 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(grapeProd).
                setType(Type.DETAIL_SALE)
                .build();

        Report report = new Report(sale1);
        report.setSale(sale2);
        report.setSale(sale3);
        report.setSale(sale4);
        assert(report.getSaleCount() == 4);
        assert(report.getSaleMap().containsKey(ProductType.BANANA));
        assert(report.getSaleMap().get(ProductType.BANANA).size() == 2 );
        assert(Sale.getSaleMap().get(ProductType.BANANA) == 175);
        assert(report.getSaleMap().containsKey(ProductType.AVOCADO));
        assert(report.getSaleMap().get(ProductType.AVOCADO).size() == 1 );
        assert(report.getSaleMap().get(ProductType.GRAPE).size() == 1 );
        report.getSaleMap().clear();
        report.getGlobalSaleList().clear();
        Sale.getSaleMap().clear();
    }

    /**
     * Test processing of detailed sales
     */
    @Test
    public void processAdjustmentAndDetailSales() throws Exception {
        Sale sale1 = new Sale.Builder().
                setNumOfSale(5).
                setProduct(bananaProd1).
                setType(Type.DETAIL_SALE)
                .build();

        Sale sale2 = new Sale.Builder().
                setNumOfSale(4).
                setProduct(bananaProd2).
                setType(Type.ADJUSTMENT_SALE).
                setAdjustment(AdjustmentType.ADD)
                .build() ;

        Sale sale3 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(avocadoProd).
                setType(Type.DETAIL_SALE)
                .build();

        Sale sale4 = new Sale.Builder().
                setNumOfSale(2).
                setProduct(avocadoProd).
                setType(Type.ADJUSTMENT_SALE).
                setAdjustment(AdjustmentType.SUBTRACT)
                .build();

        Sale sale5 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(grapeProd).
                setType(Type.DETAIL_SALE)
                .build();

        Sale sale6 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(grapeProd).
                setType(Type.ADJUSTMENT_SALE)
                .setAdjustment(AdjustmentType.ADD)
                .build();

        Sale sale7 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(appleProd2).
                setType(Type.SIMPLE_SALE)
                .build();

        Sale sale8 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(appleProd1).
                setType(Type.DETAIL_SALE)
                .build();

        Sale sale9 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(appleProd2).
                setType(Type.ADJUSTMENT_SALE)
                .setAdjustment(AdjustmentType.MULTIPLY)
                .build();

        Sale sale10 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(appleProd3).
                setType(Type.ADJUSTMENT_SALE).
                setAdjustment(AdjustmentType.ADD)
                .build();

        Sale sale11 = new Sale.Builder().
                setNumOfSale(10).
                setProduct(mangoProd1).
                setType(Type.DETAIL_SALE)
                .build();

        Report report = new Report(sale1);
        report.setSale(sale2);
        report.setSale(sale3);
        report.setSale(sale4);
        report.setSale(sale5);
        report.setSale(sale6);
        report.setSale(sale7);
        report.setSale(sale8);
        report.setSale(sale9);
        report.setSale(sale10);
        report.on10thSale();
        report.setSale(sale11);

        assert(report.getSaleCount() == 11);
        assert(report.getSaleMap().containsKey(ProductType.GRAPE));
        assert(report.getSaleMap().get(ProductType.BANANA).size() == 2 );
        assert(report.getSaleMap().containsKey(ProductType.AVOCADO));
        assert(report.getSaleMap().get(ProductType.AVOCADO).size() == 2 );
        assert(report.getSaleMap().get(ProductType.GRAPE).size() == 2 );
        assert(report.getSaleMap().get(ProductType.APPLE).size() == 4 );
        report.getSaleMap().clear();
        report.getGlobalSaleList().clear();
        Sale.getSaleMap().clear();
        Sale.getAdjustmentMap().clear();
    }


}




