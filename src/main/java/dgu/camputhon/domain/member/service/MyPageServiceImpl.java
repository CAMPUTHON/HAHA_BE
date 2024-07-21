package dgu.camputhon.domain.member.service;

import dgu.camputhon.domain.member.dto.MyPageDTO;
import dgu.camputhon.domain.member.dto.ProfileUpdateDTO;
import dgu.camputhon.domain.member.entity.Member;
import dgu.camputhon.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;

    @Override
    public MyPageDTO getProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        // Long 타입을 int 타입으로 변환
        int challengeNum = Math.toIntExact(member.getChallengeNum());
        String badges;
        int upgradeCnt;
        String upgradeBadges;

        if (challengeNum >= 50) {
            badges = "red";
            upgradeCnt = 0;
            upgradeBadges = "red";
        } else if (challengeNum >= 20) {
            badges = "purple";
            upgradeCnt = 50 - challengeNum;
            upgradeBadges = "red";
        } else if (challengeNum >= 10) {
            badges = "blue";
            upgradeCnt = 20 - challengeNum;
            upgradeBadges = "purple";
        } else if (challengeNum >= 5) {
            badges = "green";
            upgradeCnt = 10 - challengeNum;
            upgradeBadges = "blue";
        } else {
            badges = "none";
            upgradeCnt = 5 - challengeNum;
            upgradeBadges = "green";
        }

        return MyPageDTO.builder()
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .badges(badges)
                .upgradeCnt(upgradeCnt)
                .upgradeBadges(upgradeBadges)
                .build();
    }

    @Override
    public void updateProfile(Long memberId, ProfileUpdateDTO profileUpdateDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + memberId));

        member.updateProfile(profileUpdateDTO.getMemberName(), profileUpdateDTO.getEmail());
        memberRepository.save(member);
    }
}
