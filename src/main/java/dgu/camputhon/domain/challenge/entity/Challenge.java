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
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    private String challengeTitle;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private ChallengeCategory category;

    @ManyToOne
    @JoinColumn(name = "timeCategoryId")
    private TimeCategory timeCategory;

    @OneToMany(mappedBy = "challenge")
    private Set<MemberChallenge> memberChallenges;
}