package dgu.camputhon.domain.challenge.repository;

import dgu.camputhon.domain.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByCategory_ChallengeCategoryIdAndTimeCategory_TimeCategoryId(Long categoryId, Long timeCategoryId);
}
