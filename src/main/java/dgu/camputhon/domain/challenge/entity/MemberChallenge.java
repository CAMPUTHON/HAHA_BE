package dgu.camputhon.domain.challenge.entity;

import dgu.camputhon.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberChallengeId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String image;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "challengeId")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

}
