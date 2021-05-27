package models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Profile {

    @Column(nullable = true, unique = true)
    private String avatarName;
    
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Transient
    @Formula("(select count(*) from quiz q where user_id = id)")
    private int createdTestByUser;

    @Transient
    @Formula("(select count(*) from result r where user_id = id)")
    private int testPassedByUser;

    
}
