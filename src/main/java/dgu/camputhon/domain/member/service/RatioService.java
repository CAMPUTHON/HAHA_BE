package dgu.camputhon.domain.member.service;

import dgu.camputhon.domain.challenge.entity.MemberChallenge;
import dgu.camputhon.domain.member.entity.Member;
import dgu.camputhon.domain.member.repository.MemberRepository;
import dgu.camputhon.domain.member.dto.RatioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatioService {

    @Autowired
    private MemberRepository memberRepository;

    public RatioDTO calculateRatios(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        List<MemberChallenge> memberChallenges = member.getMemberChallenges().stream().toList();

        long totalChallenges = memberChallenges.size();
        if (totalChallenges == 0) {
            return new RatioDTO(0, 0, 0);
        }

        long healthCount = memberChallenges.stream()
                .filter(mc -> "HEALTH".equals(mc.getChallenge().getCategory().getChallengeCategoryName()))
                .count();
        long studyCount = memberChallenges.stream()
                .filter(mc -> "STUDY".equals(mc.getChallenge().getCategory().getChallengeCategoryName()))
                .count();
        long leisureCount = memberChallenges.stream()
                .filter(mc -> "LEISURE".equals(mc.getChallenge().getCategory().getChallengeCategoryName()))
                .count();

        double healthRatio = Math.round(((double) healthCount / totalChallenges * 100) * 10) / 10.0;
        double studyRatio = Math.round(((double) studyCount / totalChallenges * 100) * 10) / 10.0;
        double leisureRatio = Math.round(((double) leisureCount / totalChallenges * 100) * 10) / 10.0;

        return new RatioDTO(healthRatio, studyRatio, leisureRatio);
    }
}
