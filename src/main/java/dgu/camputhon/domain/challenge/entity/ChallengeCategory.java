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
public class ChallengeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeCategoryId;

    @Enumerated(EnumType.STRING)
    private ChallengeCategoryName challengeCategoryName;

    @OneToMany(mappedBy = "category")
    private Set<Challenge> challenges;

}
