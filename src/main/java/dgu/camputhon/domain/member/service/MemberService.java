package dgu.camputhon.domain.member.service;

import dgu.camputhon.domain.member.dto.MemberDTO.MemberRequest.LoginRequest;
import dgu.camputhon.domain.member.dto.MemberDTO.MemberResponse.LoginResponse;
import dgu.camputhon.domain.member.dto.MemberDTO.MemberRequest.SignupRequest;

public interface MemberService {
    void signup(SignupRequest signupRequest);
    LoginResponse login(LoginRequest loginRequest);
}
