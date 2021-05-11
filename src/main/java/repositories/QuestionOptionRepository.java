package repositories;

import models.QuestionOption;

import java.util.Optional;

public interface QuestionOptionRepository extends CrudRepository<QuestionOption> {
    Optional<QuestionOption> findByText(String text);
}
