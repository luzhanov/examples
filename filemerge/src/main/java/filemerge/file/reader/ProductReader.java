package filemerge.file.reader;

import filemerge.model.Product;

import java.io.InputStream;

public class ProductReader extends AbstractCsvBufferedReader<Product> {

    public ProductReader(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public Product createObjectFromCSVData(String[] strings) {
        Product product = new Product();

        product.setId(Long.parseLong(strings[0]));
        product.setDescription(strings[1]);

        return product;
    }
}
