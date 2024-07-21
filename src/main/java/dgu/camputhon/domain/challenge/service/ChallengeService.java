package dgu.camputhon.domain.challenge.service;
import dgu.camputhon.domain.challenge.dto.ChallengeDTO.ChallengeResponse.ChallengeGetResponse;

import java.time.LocalDate;
import java.util.List;


public interface ChallengeService {
    List<ChallengeGetResponse> getChallengesByDateAndMemberId(LocalDate date, Long memberId);
}
