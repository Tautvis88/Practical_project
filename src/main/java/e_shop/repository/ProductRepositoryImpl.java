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
        // papildomas funkcionalumas, setPrice ar pan., išrūšiuoti, suapavalinti ir tik tada save to DB
        // o DAO tik data access object tik save, update, delete
        productDao.addProduct(product);
    }
}
