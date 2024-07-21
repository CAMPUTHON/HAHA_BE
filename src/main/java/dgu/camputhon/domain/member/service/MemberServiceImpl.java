package dgu.camputhon.domain.member.service;

import dgu.camputhon.domain.member.entity.Member;
import dgu.camputhon.domain.member.dto.MemberDTO.MemberRequest.LoginRequest;
import dgu.camputhon.domain.member.dto.MemberDTO.MemberResponse.LoginResponse;
import dgu.camputhon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import dgu.camputhon.domain.member.dto.MemberDTO.MemberRequest.SignupRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getMemberEmail())
                .orElseThrow(() -> new RuntimeException("해당 멤버가 존재하지 않습니다."));

        if (!member.getMemberPassword().equals(loginRequest.getMemberPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        return LoginResponse.builder()
                .memberId(member.getMemberId().intValue())
                .build();
    }

    public void signup(SignupRequest signupRequest) {
        if (!signupRequest.getMemberPassword().equals(signupRequest.getMemberPasswordConfirm())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        boolean emailExists = memberRepository.existsByEmail(signupRequest.getMemberEmail());
        if (emailExists) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        Member member = Member.builder()
                .memberName(signupRequest.getMemberName())
                .email(signupRequest.getMemberEmail())
                .memberPassword(signupRequest.getMemberPassword())
                .challengeNum(0L)
                .build();

        memberRepository.save(member);
    }
}
