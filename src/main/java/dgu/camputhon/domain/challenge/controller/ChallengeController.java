package dgu.camputhon.domain.challenge.controller;

import dgu.camputhon.domain.challenge.service.ChallengeService;
import dgu.camputhon.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeGetResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeDetailResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.AddChallengeResponse;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;

    @GetMapping("/get/calendar")
    public ApiResponse<List<ChallengeGetResponse>> getChallenges(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long memberId) {
        List<ChallengeGetResponse> challenges = challengeService.getChallengesByDateAndMemberId(date, memberId);
        return ApiResponse.onSuccess(challenges);
    }

    @GetMapping("/get/calendar/detail/{challengeId}/{memberId}")
    public ApiResponse<ChallengeDetailResponse> getChallengeDetail(
            @PathVariable Long challengeId,
            @PathVariable Long memberId) {
        ChallengeDetailResponse challengeDetail = challengeService.getChallengeDetail(challengeId, memberId);
        return ApiResponse.onSuccess(challengeDetail);
    }

    @PostMapping("/add/{challengeId}/{memberId}")
    public ApiResponse<AddChallengeResponse> addChallenge(
            @PathVariable Long challengeId,
            @PathVariable Long memberId) {
        AddChallengeResponse response = challengeService.addChallenge(challengeId, memberId);
        return ApiResponse.onSuccess(response);
    }
}
