package models;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "result")
public class Result {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    @OneToMany(mappedBy = "answer", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Getter
    @Temporal(TemporalType.DATE)
    @Column(insertable = false, updatable = false, name = "date_pass")
    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    private LocalDate dateOfPass;

    public int getQuestionsAmount() {
        return answers.size();
    }

    public int getCorrectAnswersAmount() {
        return (int) answers.stream().filter(answer -> answer.getQuestionOption().isCorrect()).count();
    }
    public int getPercentage() {
        return getCorrectAnswersAmount() / getQuestionsAmount() * 100;
    }
}
