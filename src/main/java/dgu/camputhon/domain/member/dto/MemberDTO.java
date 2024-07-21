package dgu.camputhon.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberDTO {
    public static class MemberRequest {
        @Getter
        @Builder
        public static class LoginRequest {
            private String memberEmail;
            private String memberPassword;
        }

        @Getter
        @Builder
        public static class SignupRequest {
            private String memberName;
            private String memberEmail;
            private String memberPassword;
            private String memberPasswordConfirm;
        }
    }

    public static class MemberResponse {
        @Getter
        @Builder
        public static class LoginResponse {
            private int memberId;
        }
    }
}
