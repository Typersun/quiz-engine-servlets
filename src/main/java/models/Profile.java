package models;

import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Profile {

    @Column(nullable = true)
    private File avatar;

    @Temporal(TemporalType.DATE)
    @Column(insertable = false, updatable = false, name = "date_of_registration")
    @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
    private LocalDate dateOfRegistration;

    @Transient
    @Formula("(select count(*) from quiz q where user_id = id)")
    private int createdTestByUser;

    @Transient
    @Formula("(select count(*) from result r where user_id = id)")
    private int testPassedByUser;

    
}
