package e_shop.dao;

import e_shop.entity.Product;
import e_shop.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductDao {

    private Transaction transaction = null;
    // Generic Dao, nes abu dao labai panašūs
    public void addProduct(Product product) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            session.close();
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            exception.printStackTrace();
        }
    }
}
