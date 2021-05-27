package models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    @Size(min = 6, max = 80)
    private String username;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 100)
    private String email;

    @Column(nullable = false)
    @Size(min = 5, max = 30)
    private String password;

    @Embedded
    private Profile profile;

    @PrePersist
    public void prePersist() {
        profile.setCreatedOn(LocalDateTime.now());
    }


}
