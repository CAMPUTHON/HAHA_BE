package dgu.camputhon.domain.member.service;

import dgu.camputhon.domain.member.dto.MyPageDTO;
import dgu.camputhon.domain.member.dto.ProfileUpdateDTO;

public interface MyPageService {
    MyPageDTO getProfile(Long memberId);
    void updateProfile(Long memberId, ProfileUpdateDTO profileUpdateDTO);
}
