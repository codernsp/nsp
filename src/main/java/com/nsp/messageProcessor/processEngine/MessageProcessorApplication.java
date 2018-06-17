package com.nsp.messageProcessor.processEngine;

import com.nsp.messageProcessor.adjustment.AdjustmentType;
import com.nsp.messageProcessor.message.Message;
import com.nsp.messageProcessor.message.Type;
import com.nsp.messageProcessor.product.Product;
import com.nsp.messageProcessor.product.ProductType;
import com.nsp.messageProcessor.report.Report;
import com.nsp.messageProcessor.sale.Sale;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static jdk.nashorn.internal.objects.NativeString.toUpperCase;

/**
 * This class accepts the Messages in XML format and processes them
 * Assumption: messages arriving from salesEngine are in XML format
 */

public class MessageProcessorApplication {

    /**
     * Entry point of the application
     *
     *
     */
    public void init() {
        try {
            File fXmlFile = new File("src//main//resources//message.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            NodeList nodeList = doc.getElementsByTagName("Message");
            constructMessages(nodeList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * constructs messages
     *
     * @param nodeList
     */
    private static void constructMessages(NodeList nodeList) {
        // get node name and value
        double price = 0;
        int qty = 0;
        String adjustment = null;
        Type type = null;
        String productName = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element message = (Element) node;

                NodeList messageContent = node.getChildNodes();
                for (int j = 0; j < messageContent.getLength(); j++) {
                    Node n = messageContent.item(j);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) n;
                        if (elem.getNodeName().equalsIgnoreCase("Product")) {
                            productName = elem.getTextContent();
                            price = 0;
                            qty = 0;
                            adjustment = null;
                        }
                        if (elem.getNodeName().equalsIgnoreCase("Price")) {
                            price = Double.parseDouble(elem.getTextContent());
                        }
                        if (elem.getNodeName().equalsIgnoreCase("Quantity")) {
                            qty = Integer.parseInt(elem.getTextContent());
                        }
                        if (elem.getNodeName().equalsIgnoreCase("Adjustment")) {
                            adjustment = elem.getTextContent();
                        }
                    }
                }
            }
            constructProductSale(productName, adjustment, price, qty);
        }
    }

    /**
     * construct Sale object
     *
     * @param productName
     * @param adjustment
     * @param price
     * @param qty
     */
    private static void constructProductSale(String productName, String adjustment, double price, int qty) {
        Product product = new Product.Builder().
                setProductType(ProductType.valueOf(toUpperCase(productName))).
                setPrice(price).build();
        Type type;
        AdjustmentType adjustmentType = null;
        if (adjustment != null) {
            adjustmentType = AdjustmentType.valueOf(toUpperCase(adjustment));
            type = Type.ADJUSTMENT_SALE;
        } else if (qty == 0 && adjustment == null) {
            type = Type.SIMPLE_SALE;
        } else {
            type = Type.DETAIL_SALE;
        }

        //Create Message
        Message message = createMessage(qty, product);


        //create sale
        Sale sale = createSale(qty, adjustmentType, product, type);

        //print report
        if (sale != null) {
            Report report = new Report(sale);
        }
    }

    /**
     * Create sale
     *
     * @param qty
     * @param adjustmentType
     * @param product
     * @return
     */
    private static Sale createSale(int qty, AdjustmentType adjustmentType, Product product, Type type) {
        //create sale
        Sale sale;
        if (adjustmentType != null) {
            sale = new Sale.Builder().
                    setNumOfSale(qty).
                    setProduct(product).
                    setType(type).
                    setAdjustment(adjustmentType).build();
        } else if (qty != 0) {
            sale = new Sale.Builder().
                    setNumOfSale(qty).
                    setProduct(product).
                    setType(type).
                    setNumOfSale(qty).
                    build();
        } else {
            sale = new Sale.Builder().
                    setNumOfSale(qty).
                    setProduct(product).
                    setType(type).
                    setNumOfSale(qty).
                    build();
        }
        return sale;
    }

    /**
     * Construt Message
     *
     * @param qty
     * @param product
     * @return
     */
    private static Message createMessage(int qty, Product product) {
        //create message
        return new Message.Builder().
                setProduct(product).
                setQty(qty).
                build();
    }
}


