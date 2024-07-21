package dgu.camputhon.domain.member.service;

import dgu.camputhon.domain.member.entity.Member;
import dgu.camputhon.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member authenticateMember(String email, String password) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (password.equals(member.getMemberPassword())) {
                return member;
            }
        }
        return null;
    }
}
