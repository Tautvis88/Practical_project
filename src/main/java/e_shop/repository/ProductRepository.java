package e_shop.repository;

import e_shop.entity.Product;

public interface ProductRepository {

    Product findById(int id);

    void saveProductToDatabase(Product product);

}
