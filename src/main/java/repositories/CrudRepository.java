package repositories;

import exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T entity);
    Optional<T> findById(int id);
    void update(T entity);
    void deleteById(int id) throws NotFoundException;
    List<T> findAll();
}
