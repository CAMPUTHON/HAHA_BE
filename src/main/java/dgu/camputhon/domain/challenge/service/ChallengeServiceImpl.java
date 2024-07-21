package dgu.camputhon.domain.challenge.service;

import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeGetResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeDetailResponse;
import dgu.camputhon.domain.challenge.entity.Challenge;
import dgu.camputhon.domain.challenge.entity.MemberChallenge;
import dgu.camputhon.domain.challenge.entity.Status;
import dgu.camputhon.domain.challenge.repository.ChallengeRepository;
import dgu.camputhon.domain.challenge.repository.MemberChallengeRepository;
import dgu.camputhon.domain.member.entity.Member;
import dgu.camputhon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.AddChallengeResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.CurrentAndRecommendedChallengesResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengesResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeServiceImpl implements ChallengeService {

    private final MemberRepository memberRepository;
    private final MemberChallengeRepository memberChallengeRepository;
    private final ChallengeRepository challengeRepository;

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

    public ChallengeDetailResponse getChallengeDetail(Long challengeId, Long memberId) {
        MemberChallenge memberChallenge = memberChallengeRepository.findByChallenge_ChallengeIdAndMember_MemberId(challengeId, memberId)
                .orElseThrow(() -> new RuntimeException("챌린지를 찾을 수 없습니다."));

        return ChallengeDetailResponse.builder()
                .challengeTitle(memberChallenge.getChallenge().getChallengeTitle())
                .challengeTime(String.valueOf(memberChallenge.getChallenge().getTimeCategory().getTimeCategoryName()))
                .challengeCategory(memberChallenge.getChallenge().getCategory().getChallengeCategoryName().toString())
                .date(memberChallenge.getCompletedAt().toLocalDate().toString())
                .status(memberChallenge.getStatus().toString())
                .image(memberChallenge.getImage())
                .build();
    }

    public AddChallengeResponse addChallenge(Long challengeId, Long memberId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("챌린지를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버를 찾을 수 없습니다."));

        MemberChallenge memberChallenge = MemberChallenge.builder()
                .challenge(challenge)
                .member(member)
                .status(Status.IN_PROGRESS)
                .startedAt(LocalDateTime.now())
                .build();

        memberChallengeRepository.save(memberChallenge);

        return AddChallengeResponse.builder()
                .challengeId(challengeId)
                .memberId(memberId)
                .status(memberChallenge.getStatus().toString())
                .startDate(memberChallenge.getStartedAt().toLocalDate().toString())
                .build();
    }

    public CurrentAndRecommendedChallengesResponse getCurrentAndRecommendedChallenges(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 멤버가 없습니다."));

        List<Status> inProgressStatuses = List.of(Status.IN_PROGRESS, Status.COMPLETED);
        List<MemberChallenge> currentChallenges = memberChallengeRepository.findByMemberAndStatusIn(member, inProgressStatuses);

        List<ChallengesResponse> currentChallengesResponse = currentChallenges.stream()
                .map(memberChallenge -> ChallengesResponse.builder()
                        .challengeId(memberChallenge.getChallenge().getChallengeId())
                        .challengeTitle(memberChallenge.getChallenge().getChallengeTitle())
                        .challengeTime(memberChallenge.getChallenge().getTimeCategory().getTimeCategoryName().toString())
                        .challengeCategory(memberChallenge.getChallenge().getCategory().getChallengeCategoryName().toString())
                        .status(memberChallenge.getStatus().toString())
                        .build())
                .collect(Collectors.toList());

        List<Challenge> allChallenges = challengeRepository.findAll();
        List<Challenge> recommendedChallenges = getRandomChallenges(allChallenges, 5);

        List<ChallengesResponse> recommendedChallengesResponse = recommendedChallenges.stream()
                .map(challenge -> ChallengesResponse.builder()
                        .challengeId(challenge.getChallengeId())
                        .challengeTitle(challenge.getChallengeTitle())
                        .challengeTime(challenge.getTimeCategory().getTimeCategoryName().toString())
                        .challengeCategory(challenge.getCategory().getChallengeCategoryName().toString())
                        .build())
                .collect(Collectors.toList());

        return CurrentAndRecommendedChallengesResponse.builder()
                .currentChallenges(currentChallengesResponse)
                .recommendedChallenges(recommendedChallengesResponse)
                .build();
    }

    private List<Challenge> getRandomChallenges(List<Challenge> challenges, int count) {
        Random random = new Random();
        return random.ints(0, challenges.size())
                .distinct()
                .limit(count)
                .mapToObj(challenges::get)
                .collect(Collectors.toList());
    }
}