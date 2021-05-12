package models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne()
    @JoinColumn(name = "question_option_id")
    private QuestionOption questionOption;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User author;
}
