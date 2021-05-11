package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {
    private int id;
    private String questionText;
    private String questionOptionText;
    private String username;
}
