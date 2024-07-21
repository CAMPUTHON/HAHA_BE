package dgu.camputhon.domain.member.controller;

import dgu.camputhon.domain.member.dto.MyPageDTO;
import dgu.camputhon.domain.member.dto.ProfileUpdateDTO;
import dgu.camputhon.global.common.ApiResponse;
import dgu.camputhon.domain.member.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/get/{memberId}")
    public ResponseEntity<ApiResponse<MyPageDTO>> getProfile(@PathVariable Long memberId) {
        MyPageDTO myPageDTO = myPageService.getProfile(memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess(myPageDTO));
    }

    @PatchMapping("/update/{memberId}")
    public ResponseEntity<ApiResponse<Void>> updateProfile(@PathVariable Long memberId, @RequestBody ProfileUpdateDTO profileUpdateDTO) {
        myPageService.updateProfile(memberId, profileUpdateDTO);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }
}
