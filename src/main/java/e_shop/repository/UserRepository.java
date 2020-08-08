package e_shop.repository;

import e_shop.entity.User;

public interface UserRepository {

    User findById(int id);

    void saveUserToDatabase(User user);
}
