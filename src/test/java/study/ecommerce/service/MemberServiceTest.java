package study.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.Member;
import study.ecommerce.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

@Slf4j
@SpringBootTest
@Transactional
class MemberServiceTest {

    private final static String TEST_ID = "test-i";
    private final static String TEST_PW = "test-p";
    private final static String TEST_NAME = "test-n";
    private final static String TEST_MOBILE = "test-m";
    private final static String TEST_EMAIL = "test-e";


    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("회원가입")
    void join() {
        // when
        Long joinedId = memberService.join(TEST_ID, TEST_PW, TEST_NAME, TEST_MOBILE, TEST_EMAIL);

        //then
        Optional<Member> optionalMember = memberRepository.findById(joinedId);
        Member findMember = optionalMember.get();
        assertThat(findMember.getLoginId()).isEqualTo(TEST_ID);
        assertThat(findMember.getPassword()).isEqualTo(TEST_PW);
        assertThat(findMember.getName()).isEqualTo(TEST_NAME);
        assertThat(findMember.getMobile()).isEqualTo(TEST_MOBILE);
        assertThat(findMember.getEmail()).isEqualTo(TEST_EMAIL);

        deleteMember(findMember);
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() {
        //given
        Member member = createMember();

        //when
        Long loginMemberId = memberService.login(member.getLoginId(), member.getPassword());

        //then
        assertThat(member.getId()).isEqualTo(loginMemberId);

        deleteMember(member);
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFail() {
        //given
        Member member = createMember();

        //when
        assertThatIllegalStateException()
                .isThrownBy(() -> memberService.login(member.getLoginId(), "XXX"))
                .withMessage("아이디 또는 비밀번호가 맞지 않습니다.");

        //when
        assertThatIllegalStateException()
                .isThrownBy(() -> memberService.login("XXX", member.getPassword()))
                .withMessage("아이디 또는 비밀번호가 맞지 않습니다.");
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

    @Test
    @DisplayName("회원 탈퇴 & 탈퇴 실패")
    void withdrawal() {
        //given
        Member member = createMember();

        //when
        memberService.withdrawal(member.getLoginId(), member.getPassword());

        //then
        Optional<Member> optionalMember = memberRepository.findByLoginId(member.getLoginId());
        assertThat(optionalMember.isEmpty()).isTrue();

        //then
        Assertions.assertThatIllegalStateException()
                .isThrownBy(() -> memberService.withdrawal("aaaaa", "bbbbb"))
                .withMessage("아이디 또는 비밀번호 불일치");
        deleteMember(member);
    }

    @Test
    @DisplayName("회원 별명 수정")
    void modifyAlias() {
        //given
        Member member = createMember();

        //when
        memberService.modifyAlias(member.getId(), "newAlias");

        Member findMember = memberRepository.getById(member.getId());
        assertThat(findMember.getAlias()).isEqualTo("newAlias");
        deleteMember(member);
    }

    private Member createMember() {
        Member member = new Member(TEST_ID, TEST_PW, TEST_NAME, TEST_MOBILE, TEST_EMAIL);
        memberRepository.save(member);
        return member;
    }

    private void deleteMember(Member member) {
        memberRepository.delete(member);
    }


}
