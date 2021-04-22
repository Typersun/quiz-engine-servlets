package models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Question {
    private int id;
    private String text;
    private List<QuestionOption> questionOptions;
    private Quiz quiz;

}
