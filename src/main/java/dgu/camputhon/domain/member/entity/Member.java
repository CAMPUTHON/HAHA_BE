package dgu.camputhon.domain.member.entity;

import dgu.camputhon.domain.challenge.entity.MemberChallenge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String memberPassword;
    private String memberName;
    private Long challengeNum;
    private Long timeSum;

    @OneToMany(mappedBy = "member")
    private Set<MemberChallenge> memberChallenges;
}