package e_shop.dao;

import e_shop.entity.User;
import e_shop.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao {

    private Transaction transaction = null;

    public void addUser(User user) {
        try {
            Session session = HibernateUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(user);
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
