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
            private Long challengeId;
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
            private List<CurrentChallengesResponse> currentChallenges;
            private List<CurrentChallengesResponse> recommendedChallenges;
        }

        @Getter
        @Builder
        public static class CurrentChallengesResponse {
            private Long challengeId;
            private String challengeTitle;
            private String challengeTime;
            private String challengeCategory;
            private String status;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        public static class CurrentChallengeDetailResponse {
            private Long challengeId;
            private String challengeTitle;
            private String challengeTime;
            private String challengeCategory;
            private String status;
            private String description;
        }

        @Getter
        @Builder
        @AllArgsConstructor
        public static class ChallengeSearchResponse {
            private Long challengeId;
            private String challengeTitle;
            private String challengeTime;
            private String challengeCategory;
        }
    }
}
