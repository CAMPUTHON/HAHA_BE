package dgu.camputhon.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageDTO {
    private String memberName;
    private String email;
    private String badges;
    private int upgradeCnt;
    private String upgradeBadges;
}
