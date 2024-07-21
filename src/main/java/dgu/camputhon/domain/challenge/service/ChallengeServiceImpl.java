package dgu.camputhon.domain.challenge.service;

import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeGetResponse;
import dgu.camputhon.domain.challenge.entity.Challenge;
import dgu.camputhon.domain.challenge.entity.MemberChallenge;
import dgu.camputhon.domain.challenge.repository.MemberChallengeRepository;
import dgu.camputhon.domain.member.entity.Member;
import dgu.camputhon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final MemberRepository memberRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    @Override
    public List<ChallengeGetResponse> getChallengesByDateAndMemberId(LocalDate date, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 멤버가 없습니다."));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<MemberChallenge> memberChallenges = memberChallengeRepository.findByMemberAndCompletedAtBetween(member, startOfDay, endOfDay);
        if (memberChallenges.isEmpty()) {
            return null;
        }

        return memberChallenges.stream()
                .map(memberChallenge -> ChallengeGetResponse.builder()
                        .challengeTitle(memberChallenge.getChallenge().getChallengeTitle())
                        .challengeTime(memberChallenge.getChallenge().getTimeCategory().getTimeCategoryName().toString())
                        .challengeCategory(memberChallenge.getChallenge().getCategory().getChallengeCategoryName().toString())
                        .build())
                .collect(Collectors.toList());
    }
}
