package filemerge.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PriceGroup extends Product {

    private List<BigDecimal> prices = new ArrayList<>();

    public PriceGroup(Product product) {
        this.setId(product.getId());
        this.setDescription(product.getDescription());
    }

    public PriceGroup(Long id, String description, BigDecimal... prices) {
        this.setId(id);
        this.setDescription(description);
        this.addPrices(Arrays.asList(prices));
    }

    public List<BigDecimal> getPrices() {
        return prices;
    }

    public void addPrice(BigDecimal price) {
        prices.add(price.setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    private void addPrices(List<BigDecimal> prices) {
        for (BigDecimal price : prices) {
            addPrice(price);
        }
    }
}
