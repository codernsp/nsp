This is a message processing system. The entry point of the application is from the class Main.java which calls
MessageProcessorApplication.java. This class expects SalesEngine to pass messages in XML format. The class parses and
processes Messages.
There are spring wireups existing for various objects.

A Sale contains Product, and maintains a map of <ProductType, Double>. This is the total sales per product type.
A Sale can be of simple, detailed or adjustment type, pricing calculation varies for each type.
A Product class contains ProductType and price of product.
A Report class contains Sale and maintains a saleList and map of <ProductType, List<Sale>
Report class reports on every 10th sale, number of sales per product type and total value.
On every 50th Sale it reports that application is pausing and reports total adjustments per product type.
