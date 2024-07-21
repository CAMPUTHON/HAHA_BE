package dgu.camputhon.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ChallengeDTO {

    public static class ChallengeResponse {
        @Getter
        @Builder
        @AllArgsConstructor
        public static class ChallengeGetResponse {
            private String challengeTitle;
            private String challengeTime;
            private String challengeCategory;
        }
    }
}
