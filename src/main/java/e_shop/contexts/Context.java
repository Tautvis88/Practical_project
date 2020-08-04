package e_shop.contexts;

import e_shop.entity.Product;
import e_shop.strategy.DataReadingStrategy;

public class Context {

    private DataReadingStrategy dataReadingStrategy;

    public Context(DataReadingStrategy dataReadingStrategy) {
        this.dataReadingStrategy = dataReadingStrategy;
    }

    public Product executeStrategy() {
        return dataReadingStrategy.readProductData();
    }
}
