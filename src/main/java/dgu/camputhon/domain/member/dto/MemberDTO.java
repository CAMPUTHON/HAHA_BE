package dgu.camputhon.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String memberName;
    private String memberEmail;
    private String memberPassword;
    private String memberPasswordConfirm;
}
