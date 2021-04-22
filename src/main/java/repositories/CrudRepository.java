package repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    void save(T entity);
    Optional<T> findById(int id);
    void update(T entity);
    void deleteById(int id);
    List<T> findAll();
}