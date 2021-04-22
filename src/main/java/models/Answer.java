package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    private int id;
    private Question question;
    private QuestionOption questionOption;
    private User author;
}
