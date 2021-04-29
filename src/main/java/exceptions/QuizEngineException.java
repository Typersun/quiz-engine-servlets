package exceptions;

import dto.errors.ErrorEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizEngineException extends Exception {
    private ErrorEntity errorEntity;
}
