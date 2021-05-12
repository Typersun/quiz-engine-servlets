package repositories.hibernate_impl;

import lombok.AllArgsConstructor;
import models.User;
import repositories.UserRepository;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepositoryHibernateImpl implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public void save(User entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }
    @Override
    public Optional<User> findOneByUsername(String username) {


        return Optional.empty();
    }

    @Override
    public void updateWithoutPassword(User entity) {

    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
