package dgu.camputhon.domain.challenge.controller;

import dgu.camputhon.config.S3Manager;
import dgu.camputhon.domain.challenge.service.ChallengeService;
import dgu.camputhon.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeGetResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeDetailResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.AddChallengeResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.CurrentAndRecommendedChallengesResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.CurrentChallengeDetailResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeSearchResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;
    private final S3Manager s3Manager;

    // 완료한 챌린지 조회
    @GetMapping("/get/calendar")
    public ApiResponse<List<ChallengeGetResponse>> getChallenges(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long memberId) {
        List<ChallengeGetResponse> challenges = challengeService.getChallengesByDateAndMemberId(date, memberId);
        return ApiResponse.onSuccess(challenges);
    }

    // 완료한 챌린지 상세 조회
    @GetMapping("/get/calendar/detail/{challengeId}/{memberId}")
    public ApiResponse<ChallengeDetailResponse> getChallengeDetail(
            @PathVariable Long challengeId,
            @PathVariable Long memberId) {
        ChallengeDetailResponse challengeDetail = challengeService.getChallengeDetail(challengeId, memberId);
        return ApiResponse.onSuccess(challengeDetail);
    }

    // 챌린지 도전하기
    @PostMapping("/add/{challengeId}/{memberId}")
    public ApiResponse<AddChallengeResponse> addChallenge(
            @PathVariable Long challengeId,
            @PathVariable Long memberId) {
        AddChallengeResponse response = challengeService.addChallenge(challengeId, memberId);
        return ApiResponse.onSuccess(response);
    }

    // 진행 중인 챌린지 조회
    @GetMapping("/get/{memberId}")
    public ApiResponse<CurrentAndRecommendedChallengesResponse> getCurrentAndRecommendedChallenges(
            @PathVariable Long memberId) {
        CurrentAndRecommendedChallengesResponse response = challengeService.getCurrentAndRecommendedChallenges(memberId);
        return ApiResponse.onSuccess(response);
    }

    // 진행 중인 챌린지 상세 조회
    @GetMapping("/get/detail/{challengeId}/{memberId}")
    public ApiResponse<CurrentChallengeDetailResponse> getCurrentChallengeDetail(
            @PathVariable Long challengeId,
            @PathVariable Long memberId) {
        CurrentChallengeDetailResponse response = challengeService.getCurrentChallengeDetail(challengeId, memberId);
        return ApiResponse.onSuccess(response);
    }

    // 챌린지 검색
    @GetMapping("/search")
    public ApiResponse<List<ChallengeSearchResponse>> searchChallenges(
            @RequestParam String category,
            @RequestParam String time) {
        List<ChallengeSearchResponse> response = challengeService.searchChallenges(category, time);
        return ApiResponse.onSuccess(response);
    }

    // 챌린지 완료 처리
    @PostMapping("/complete/{challengeId}/{memberId}")
    public ApiResponse<String> completeChallenge(
            @PathVariable Long challengeId,
            @PathVariable Long memberId,
            @RequestParam("file") MultipartFile file) {
        String imageUrl = s3Manager.uploadFile(file);
        return ApiResponse.onSuccess(challengeService.completeChallenge(challengeId, memberId, imageUrl));
    }
}
