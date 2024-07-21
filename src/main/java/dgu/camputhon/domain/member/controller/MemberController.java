package dgu.camputhon.domain.member.controller;

import dgu.camputhon.domain.member.dto.MemberDTO.MemberRequest.LoginRequest;
import dgu.camputhon.domain.member.dto.MemberDTO.MemberResponse.LoginResponse;
import dgu.camputhon.domain.member.service.MemberService;
import dgu.camputhon.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import dgu.camputhon.domain.member.dto.MemberDTO.MemberRequest.SignupRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<String> signup(@RequestBody SignupRequest signupRequest) {
        memberService.signup(signupRequest);
        return ApiResponse.onSuccess("ok");
    }
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = memberService.login(loginRequest);
        return ApiResponse.onSuccess(loginResponse);
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        return ApiResponse.onSuccess("로그아웃");
    }
}
