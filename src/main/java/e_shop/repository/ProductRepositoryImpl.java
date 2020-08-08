package e_shop.repository;

import e_shop.dao.ProductDao;
import e_shop.entity.Product;

public class ProductRepositoryImpl implements ProductRepository {

    private ProductDao productDao = new ProductDao();

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public void saveProductToDatabase(Product product) {
        productDao.addProduct(product);
    }
}
