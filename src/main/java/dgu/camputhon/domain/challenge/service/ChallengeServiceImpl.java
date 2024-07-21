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
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.CurrentChallengesResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.CurrentChallengeDetailResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeSearchResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
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
                        .challengeId(memberChallenge.getChallenge().getChallengeId())
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

        List<CurrentChallengesResponse> currentChallengesResponse = currentChallenges.stream()
                .map(memberChallenge -> CurrentChallengesResponse.builder()
                        .challengeId(memberChallenge.getChallenge().getChallengeId())
                        .challengeTitle(memberChallenge.getChallenge().getChallengeTitle())
                        .challengeTime(memberChallenge.getChallenge().getTimeCategory().getTimeCategoryName().toString())
                        .challengeCategory(memberChallenge.getChallenge().getCategory().getChallengeCategoryName().toString())
                        .status(memberChallenge.getStatus().toString())
                        .build())
                .collect(Collectors.toList());

        List<Challenge> allChallenges = challengeRepository.findAll();
        List<Challenge> recommendedChallenges = getRandomChallenges(allChallenges, 5);

        List<CurrentChallengesResponse> recommendedChallengesResponse = recommendedChallenges.stream()
                .map(challenge -> CurrentChallengesResponse.builder()
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

    public CurrentChallengeDetailResponse getCurrentChallengeDetail(Long challengeId, Long memberId) {
        MemberChallenge memberChallenge = memberChallengeRepository.findByChallenge_ChallengeIdAndMember_MemberId(challengeId, memberId)
                .orElseThrow(() -> new RuntimeException("진행 중인 챌린지를 찾을 수 없습니다."));

        return CurrentChallengeDetailResponse.builder()
                .challengeId(challengeId)
                .challengeTitle(memberChallenge.getChallenge().getChallengeTitle())
                .challengeTime(memberChallenge.getChallenge().getTimeCategory().getTimeCategoryName().toString())
                .challengeCategory(memberChallenge.getChallenge().getCategory().getChallengeCategoryName().toString())
                .status(memberChallenge.getStatus().toString())
                .description(memberChallenge.getChallenge().getChallengeCategoryDescription())
                .build();
    }

    public List<ChallengeSearchResponse> searchChallenges(String category, String time) {
        Long categoryId = Long.valueOf(convertCategoryToId(category));
        Long timeCategoryId = Long.valueOf(convertTimeToId(time));

        List<Challenge> filteredChallenges = challengeRepository.findByCategory_ChallengeCategoryIdAndTimeCategory_TimeCategoryId(categoryId, timeCategoryId);
        Collections.shuffle(filteredChallenges);
        List<Challenge> randomChallenges = filteredChallenges.stream().limit(10).collect(Collectors.toList());

        return randomChallenges.stream()
                .map(challenge -> ChallengeSearchResponse.builder()
                        .challengeId(challenge.getChallengeId())
                        .challengeTitle(challenge.getChallengeTitle())
                        .challengeTime(challenge.getTimeCategory().getTimeCategoryName())
                        .challengeCategory(challenge.getCategory().getChallengeCategoryName())
                        .build())
                .collect(Collectors.toList());
    }

    private Integer convertCategoryToId(String category) {
        switch (category.toUpperCase()) {
            case "HEALTH":
                return 1;
            case "STUDY":
                return 2;
            case "LEISURE":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    private Integer convertTimeToId(String time) {
        switch (time.toLowerCase()) {
            case "30min":
                return 1;
            case "1hour":
                return 2;
            case "2hours":
                return 3;
            case "3hours":
                return 4;
            case "4hours":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid time: " + time);
        }
    }

    public String completeChallenge(Long challengeId, Long memberId, String imageUrl) {
        MemberChallenge memberChallenge = memberChallengeRepository.findByChallenge_ChallengeIdAndMember_MemberId(challengeId, memberId)
                .orElseThrow(() -> new RuntimeException("진행 중인 챌린지를 찾을 수 없습니다."));

        // 상태를 COMPLETED로 변경하고 완료 시간을 설정
        memberChallenge.completeChallenge(imageUrl);
        memberChallengeRepository.save(memberChallenge);

        // 멤버의 챌린지 수를 증가
        Member member = memberChallenge.getMember();
        member.updateChallengeNum(member.getChallengeNum() + 1);
        memberRepository.save(member);

        return imageUrl;
    }
}