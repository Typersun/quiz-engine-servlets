package repositories;

import models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> findOneByUsername(String username);
    void updateWithoutPassword(User entity);
    Optional<User> findOneByEmail(String email);
    Optional<User> isFileExist(String filename);
    void saveAvatarByUserId(String uuid, int id);
    Optional<String> isUserAlreadyHaveAvatar(int id);
}
