package study.ecommerce.service;

public interface MemberService {

    // 회원가입 / 로그인
    Long join(String loginId, String password, String name, String mobile, String email);
    Long login(String loginId, String password);

    // 아이디 중복 조회
    boolean checkLoginIdDuplication(String loginId);
}
