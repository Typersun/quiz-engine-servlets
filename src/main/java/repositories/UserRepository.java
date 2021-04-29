package repositories;

import models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> findOneByUsername(String username);
    void updateWithoutPassword(User entity);
}
