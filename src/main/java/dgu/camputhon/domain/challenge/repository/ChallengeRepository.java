package dgu.camputhon.domain.challenge.repository;

import dgu.camputhon.domain.challenge.entity.Challenge;
import dgu.camputhon.domain.challenge.entity.MemberChallenge;
import dgu.camputhon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
