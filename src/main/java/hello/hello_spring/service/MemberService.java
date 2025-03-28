package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
//@Service 컴포넌트 스캔
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired 컴포넌트 스캔
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {

        // 중복 회원 검증 (같은 이름을 가진 회원은 존재할 수 없다)
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();

        // 메소드 호출시간 측정 로직 추가
        /*long start = System.currentTimeMillis();

        try{
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
        } finally {
            // 측정시간 로그
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }*/
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();

        // 메소드 호출시간 측정 로직 추가
        /*long start = System.currentTimeMillis();

        try{
            return memberRepository.findAll();
        } finally {
            // 측정시간 로그
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }*/
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
