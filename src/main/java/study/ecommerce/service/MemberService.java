package study.ecommerce.service;

public interface MemberService {

    // 회원가입 / 로그인
    Long join(String loginId, String password, String name, String mobile, String email);
    Long login(String loginId, String password);

    // 아이디 중복 조회
    boolean checkLoginIdDuplication(String loginId);

    // 회원 탈퇴
    void withdrawal(String loginId, String password);

    // 닉네임 변경
    void modifyAlias(Long memberId, String alias);
}

