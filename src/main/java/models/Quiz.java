package models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quiz {
    private int id;
    private String name;
    private List<Question> questions;
    private User user;
}
