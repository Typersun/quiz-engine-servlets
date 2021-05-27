package repositories.hibernate_impl;

import exceptions.NotFoundException;
import repositories.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public abstract class GenericCrudImpl<T> implements CrudRepository<T> {

    protected final EntityManager entityManager;
    protected final Class<T> entityClass;

    protected GenericCrudImpl(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
    }


    @Override
    public void save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public Optional<T> findById(int id) {
        return Optional.ofNullable(entityManager.find(entityClass, id, LockModeType.NONE));
    }

    @Override
    public void update(T entity) {
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(int id) throws NotFoundException {
        Optional<T> entity = findById(id);
        entityManager.getTransaction().begin();
        entityManager.remove(entity.orElseThrow(NotFoundException::new));
        entityManager.getTransaction().commit();
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
