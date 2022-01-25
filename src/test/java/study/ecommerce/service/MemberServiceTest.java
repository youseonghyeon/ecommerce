package study.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.Member;
import study.ecommerce.repository.MemberRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

    private final static String TEST_ID = "test";
    private final static String TEST_PW = "test";
    private final static String TEST_NAME = "test";

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입")
    void join() {
        // when
        Long joinId = memberService.join("id", "pw", "name", "010-1111-1111", "email@gmail.com");

        //then
        Optional<Member> optionalMember = memberRepository.findById(joinId);
        Member findMember = optionalMember.get();
        assertThat(findMember.getLoginId()).isEqualTo("id");
        assertThat(findMember.getPassword()).isEqualTo("pw");
        assertThat(findMember.getName()).isEqualTo("name");
        assertThat(findMember.getMobile()).isEqualTo("010-1111-1111");
        assertThat(findMember.getEmail()).isEqualTo("email@gmail.com");

        deleteMember(findMember);
    }

    @Test
    @DisplayName("로그인")
    void login() {
        //given
        Member member = createMember();

        //when
        Long loginMemberId = memberService.login(member.getLoginId(), member.getPassword());

        //then
        assertThat(member.getId()).isEqualTo(loginMemberId);

        deleteMember(member);
    }


    @Test
    @DisplayName("아이디 중복확인")
    void checkLoginIdDuplication() {
        //given
        Member member = createMember();

        //when
        boolean fail = memberService.checkLoginIdDuplication(TEST_ID);
        boolean success = memberService.checkLoginIdDuplication(UUID.randomUUID().toString().substring(0, 8));
        assertThat(fail).isFalse();
        assertThat(success).isTrue();

        deleteMember(member);
    }

    private Member createMember() {
        Member member = new Member(TEST_ID, TEST_PW, TEST_NAME, "010-1111-1111", "email@gmail.com");
        memberRepository.save(member);
        return member;
    }

    private void deleteMember(Member member) {
        memberRepository.delete(member);
    }


}
