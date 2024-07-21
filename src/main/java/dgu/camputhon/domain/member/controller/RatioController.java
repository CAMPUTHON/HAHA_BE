package dgu.camputhon.domain.member.controller;

import dgu.camputhon.domain.member.dto.RatioDTO;
import dgu.camputhon.domain.member.service.RatioService;
import dgu.camputhon.global.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratio")
public class RatioController {

    @Autowired
    private RatioService ratioService;

    @GetMapping("/{memberId}")
    public ApiResponse<RatioDTO> getRatio(@PathVariable Long memberId) {
        RatioDTO ratioDTO = ratioService.calculateRatios(memberId);
        return ApiResponse.onSuccess(ratioDTO);
    }
}
