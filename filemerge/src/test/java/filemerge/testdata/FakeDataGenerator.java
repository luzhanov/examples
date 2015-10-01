package filemerge.testdata;

import filemerge.model.Price;
import filemerge.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class FakeDataGenerator {

    private final static Random random = new Random();

    public List<Product> generateProductData(int numberOfData) {
        List<Product> resultList = new ArrayList<>();

        for (int i = 0; i < numberOfData; i++) {
            Product newProduct = new Product();
            newProduct.setId((long) i);
            newProduct.setDescription("product" + i);
            resultList.add(newProduct);
        }

        return resultList;
    }

    public List<Price> generatePricesForProducts(List<Product> products) {
        List<Price> productPrices = new ArrayList<>();

        for (Product product : products) {
            int priceCount = random.nextInt(10) + 2;

            for (int i = 0; i < priceCount; i++) {
                Price newPrice = new Price();
                newPrice.setProductId(product.getId());
                newPrice.setDate(new Date());

                Double priceValue = (random.nextDouble() * 10) + 1;
                newPrice.setPrice(new BigDecimal(priceValue).setScale(2, BigDecimal.ROUND_HALF_UP));

                productPrices.add(newPrice);
            }
        }

        return productPrices;
    }
}
