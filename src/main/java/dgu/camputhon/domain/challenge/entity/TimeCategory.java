package dgu.camputhon.domain.challenge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TimeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeCategoryId;

    private String timeCategoryName;

    @OneToMany(mappedBy = "timeCategory")
    private Set<Challenge> challenges;
}
