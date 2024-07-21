package dgu.camputhon.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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

        @Getter
        @Builder
        @AllArgsConstructor
        public static class ChallengeDetailResponse {
            private String challengeTitle;
            private String challengeTime;
            private String challengeCategory;
            private String date;
            private String status;
            private String image;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        public static class AddChallengeResponse {
            private Long challengeId;
            private Long memberId;
            private String status;
            private String startDate;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        public static class CurrentAndRecommendedChallengesResponse {
            private List<ChallengesResponse> currentChallenges;
            private List<ChallengesResponse> recommendedChallenges;
        }

        @Getter
        @Builder
        public static class ChallengesResponse {
            private Long challengeId;
            private String challengeTitle;
            private String challengeTime;
            private String challengeCategory;
            private String status;
        }
    }
}
