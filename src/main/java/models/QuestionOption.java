package models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOption {
    private int id;
    private String text;
    private boolean isCorrect;
    private Question question;
}
