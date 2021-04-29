package repositories;

import models.Quiz;

import java.util.Optional;

public interface QuizRepository extends CrudRepository<Quiz> {
    Optional<Quiz> findByName(String name);
}
