package dgu.camputhon.domain.challenge.repository;

import dgu.camputhon.domain.challenge.entity.MemberChallenge;
import dgu.camputhon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {
    List<MemberChallenge> findByMemberAndCompletedAtBetween(Member member, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
