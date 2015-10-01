package filemerge.testdata;

import filemerge.Utils;
import filemerge.model.Price;
import filemerge.model.Product;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TestDataCreator {

    public static void main(String[] args) {
        FakeDataGenerator dataGenerator = new FakeDataGenerator();

        List<Product> productList = dataGenerator.generateProductData(100000);
        List<Price> priceList = dataGenerator.generatePricesForProducts(productList);

        Collections.shuffle(productList);
        Collections.shuffle(priceList);

        try {
            FileOutputStream productsOs = Utils.createFileOutputStream("products1.csv");
            FileOutputStream pricesOs = Utils.createFileOutputStream("prices1.csv");

            ProductWriter productWriter = new ProductWriter(productsOs);
            ProductPriceWriter priceWriter = new ProductPriceWriter(pricesOs);

            for (Product product : productList) {
                productWriter.writeObject(product);
            }
            System.out.println("products file generated");

            for (Price price : priceList) {
                priceWriter.writeObject(price);
            }

            System.out.println("prices file generated");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
