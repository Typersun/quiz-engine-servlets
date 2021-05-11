package repositories;

import models.Question;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question>{
    Optional<Question> findByText(String text);
}
