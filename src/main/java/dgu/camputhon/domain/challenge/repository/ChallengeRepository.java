package dgu.camputhon.domain.challenge.repository;

import dgu.camputhon.domain.challenge.entity.Challenge;
import dgu.camputhon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
