package study.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.Member;
import study.ecommerce.repository.MemberRepository;
import study.ecommerce.security.SHA256Util;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long join(
            String loginId,
            String password,
            String name,
            String mobile,
            String email) {
        Member newMember = new Member(loginId, password, name, mobile, email);
        memberRepository.save(newMember);

        return newMember.getId();
    }

    @Override
    public Long login(String loginId, String password) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isEmpty()) throw new IllegalStateException("아이디 또는 비밀번호가 맞지 않습니다.");
        Member member = optionalMember.get();
        // 요청된 비밀번호 암호화
        String encryptedPW = SHA256Util.getEncrypt(password, member);

        String findPW = member.getPassword();

        if (!findPW.equals(encryptedPW)) throw new IllegalStateException("아이디 또는 비밀번호가 맞지 않습니다.");
        else return member.getId();
    }

    @Override
    public boolean checkLoginIdDuplication(String loginId) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        return optionalMember.isEmpty();

    }

    @Override
    @Transactional
    public void withdrawal(String loginId, String password) {
        Member member = findMember(loginId, "아이디 또는 비밀번호 불일치");
        String encrypt = SHA256Util.getEncrypt(password, member);

        if (member.getPassword().equals(encrypt)) {
            memberRepository.delete(member);
        }
        throw new IllegalStateException("아이디 또는 비밀번호 불일치");
    }

    @Override
    @Transactional
    public void modifyAlias(Long memberId, String alias) {
        Member findMember = memberRepository.getById(memberId);
        findMember.aliasModify(alias);
    }

    private Member findMember(String loginId, String ExMessage) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isEmpty()) throw new IllegalStateException(ExMessage);
        return optionalMember.get();
    }
}
