package dgu.camputhon.domain.member.controller;

import dgu.camputhon.domain.member.dto.MemberDTO;
import dgu.camputhon.domain.member.dto.LoginDTO;
import dgu.camputhon.domain.member.dto.ResponseDTO;
import dgu.camputhon.domain.member.entity.Member;
import dgu.camputhon.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api") // API 경로 설정
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> registerMember(@RequestBody MemberDTO memberDTO) {
        if (!memberDTO.getMemberPassword().equals(memberDTO.getMemberPasswordConfirm())) {
            return ResponseEntity.badRequest().body(
                    new ResponseDTO(false, "COMMON400", "비밀번호가 일치하지 않습니다.", null)
            );
        }
        Member member = Member.builder()
                .memberName(memberDTO.getMemberName())
                .email(memberDTO.getMemberEmail())
                .memberPassword(memberDTO.getMemberPassword())
                .build();
        memberService.registerMember(member);
        return ResponseEntity.ok(new ResponseDTO(true, "COMMON200", "성공입니다.", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Member member = memberService.authenticateMember(loginDTO.getMemberEmail(), loginDTO.getMemberPassword());
        if (member != null) {
            return ResponseEntity.ok(new ResponseDTO(true, "COMMON200", "성공입니다.", null));
        }
        return ResponseEntity.status(400).body(new ResponseDTO(false, "COMMON400", "이메일 또는 비밀번호가 잘못되었습니다.", null));
    }
}
