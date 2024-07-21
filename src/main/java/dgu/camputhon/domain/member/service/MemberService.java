package dgu.camputhon.domain.member.service;

import dgu.camputhon.domain.member.entity.Member;

public interface MemberService {
    Member registerMember(Member member);
    Member authenticateMember(String email, String password);
}
