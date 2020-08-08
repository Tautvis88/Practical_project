package e_shop.repository;

import e_shop.dao.UserDao;
import e_shop.entity.User;

public class UserRepositoryImpl implements UserRepository {

    private UserDao userDao = new UserDao();

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public void saveUserToDatabase(User user) {
        userDao.addUser(user);
    }
}
