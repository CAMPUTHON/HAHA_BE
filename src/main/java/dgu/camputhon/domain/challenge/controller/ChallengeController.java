package dgu.camputhon.domain.challenge.controller;

import dgu.camputhon.domain.challenge.service.ChallengeService;
import dgu.camputhon.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeGetResponse;

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
}
