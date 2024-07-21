package dgu.camputhon.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String memberEmail;
    private String memberPassword;
}