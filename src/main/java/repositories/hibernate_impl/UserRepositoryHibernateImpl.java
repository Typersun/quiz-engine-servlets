package repositories.hibernate_impl;

import models.Profile;
import models.User;
import repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Optional;

public class UserRepositoryHibernateImpl extends GenericCrudImpl<User> implements UserRepository {


    public UserRepositoryHibernateImpl(Class<User> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.username = :value", User.class)
                    .setParameter("value", username)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateWithoutPassword(User entity) {
        User user = entityManager.createQuery("select u from User u where u.id = :id", User.class).setParameter("id", entity.getId()).getSingleResult();
        entityManager.getTransaction().begin();
        user.setUsername(entity.getUsername());
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    public Optional<User> findOneByEmail(String email) {
        try {
            return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.email = :value", User.class)
                    .setParameter("value", email)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> isFileExist(String filename) {
        Optional<User> user = Optional.ofNullable(entityManager.createQuery("SELECT u FROM User u where u.profile.avatarName = :value", User.class)
                .setParameter("value", filename)
                .getResultList().stream().findFirst().orElse(null));
        return user;


    }

    @Override
    public void saveAvatarByUserId(String uuid, int id) {
       User user = findById(id).get();
       entityManager.getTransaction().begin();
       user.getProfile().setAvatarName(uuid);
       entityManager.merge(user);
       entityManager.getTransaction().commit();

    }

    @Override
    public Optional<String> isUserAlreadyHaveAvatar(int id) {
        return Optional.ofNullable(entityManager.createQuery("SELECT u.profile.avatarName FROM User u WHERE u.id = :value", String.class)
                .setParameter("value", id).getSingleResult());
    }


}
