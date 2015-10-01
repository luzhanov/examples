package filemerge.service;

import filemerge.file.reader.PriceReader;
import filemerge.file.reader.ProductReader;
import filemerge.file.writer.ProductPriceContainerWriter;
import filemerge.model.Price;
import filemerge.model.PriceGroup;
import filemerge.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Service
public class ProductPriceMergeService {

    private static Logger logger = LoggerFactory.getLogger(ProductPriceMergeService.class);

    @SuppressWarnings("UnusedAssignment")
    public void mergeSortedProductPricesToCsvFile(InputStream productsIs, InputStream pricesIs, OutputStream resultOs) {
        try {
            validateParams(productsIs, pricesIs, resultOs);

            ProductReader productReader = new ProductReader(productsIs);
            PriceReader priceReader = new PriceReader(pricesIs);
            ProductPriceContainerWriter priceContainerWriter = new ProductPriceContainerWriter(resultOs);

            Product product = null;
            Price price = null;
            List<Price> skippedPrices = new ArrayList<>();

            while ((product = productReader.readObject()) != null) {
                PriceGroup priceGroup = new PriceGroup(product);
                boolean priceIdNotBiggerThanProductId = true;

                addSkippedPrices(product, skippedPrices, priceGroup);

                while (priceIdNotBiggerThanProductId) {
                    price = priceReader.readObject();
                    if (price == null) {  //end of file
                        priceContainerWriter.writeObject(priceGroup);
                        break;
                    } else if(priceGroup.getId().equals(price.getProductId())) {
                        priceGroup.addPrice(price.getPrice());
                    } else {
                        skippedPrices.add(price);
                        if (price.getProductId() > priceGroup.getId()) {
                            priceContainerWriter.writeObject(priceGroup);
                            priceIdNotBiggerThanProductId = false;
                        }
                    }
                }
            }

            productReader.close();
            priceReader.close();
            priceContainerWriter.close();
        } catch (IOException e) {
            logger.error("Error during file writing", e);
        } finally {
            try {
                if (productsIs != null) {
                    productsIs.close();
                }
                if (pricesIs != null) {
                    pricesIs.close();
                }
                if (resultOs != null) {
                    resultOs.close();
                }
            } catch (IOException e1) {
                logger.error("Error during stream closing", e1);
            }
        }
    }

    private void addSkippedPrices(Product product, List<Price> skippedPrices, PriceGroup priceGroup) {
        if (!skippedPrices.isEmpty()) {
            ListIterator<Price> skippedPriceIterator = skippedPrices.listIterator();
            while (skippedPriceIterator.hasNext()) {
                Price currentPrice = skippedPriceIterator.next();
                if (currentPrice.getProductId().equals(product.getId())) {
                    priceGroup.addPrice(currentPrice.getPrice());
                    skippedPriceIterator.remove();
                } else if (currentPrice.getProductId() < product.getId()) {
                    skippedPriceIterator.remove();
                }
            }
        }
    }

    private void validateParams(InputStream productsIs, InputStream pricesIs, OutputStream resultOs) {
        if (productsIs == null) {
            throw new IllegalArgumentException("productsIs in null");
        }
        if (pricesIs == null) {
            throw new IllegalArgumentException("pricesIs in null");
        }
        if (resultOs == null) {
            throw new IllegalArgumentException("resultOs in null");
        }
    }
}
