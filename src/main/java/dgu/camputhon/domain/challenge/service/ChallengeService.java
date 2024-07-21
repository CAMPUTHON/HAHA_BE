package dgu.camputhon.domain.challenge.service;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeGetResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeDetailResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.AddChallengeResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.CurrentAndRecommendedChallengesResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.CurrentChallengeDetailResponse;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeSearchResponse;

import java.time.LocalDate;
import java.util.List;


public interface ChallengeService {
    List<ChallengeGetResponse> getChallengesByDateAndMemberId(LocalDate date, Long memberId);
    ChallengeDetailResponse getChallengeDetail(Long challengeId, Long memberId);
    AddChallengeResponse addChallenge(Long challengeId, Long memberId);
    CurrentAndRecommendedChallengesResponse getCurrentAndRecommendedChallenges(Long memberId);
    CurrentChallengeDetailResponse getCurrentChallengeDetail(Long challengeId, Long memberId);
    List<ChallengeSearchResponse> searchChallenges(String category, String time);
    String completeChallenge(Long challengeId, Long memberId, String imageUrl);
}
