package dto;

import lombok.*;
import models.Question;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionOptionDto {
    private int id;
    private String text;
    private boolean isCorrect;
    private int questionId;
}
