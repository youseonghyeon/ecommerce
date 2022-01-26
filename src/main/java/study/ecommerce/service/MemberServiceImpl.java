package study.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.Member;
import study.ecommerce.repository.MemberRepository;

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
        Member saveMember = memberRepository.save(new Member(loginId, password, name, mobile, email));

        if (saveMember.getId() == null) throw new IllegalStateException("회원가입 로직 오류");
        return saveMember.getId();
    }

    @Override
    public Long login(String loginId, String password) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isPresent()) {
            Member findMember = optionalMember.get();
            if (password.equals(findMember.getPassword())) {
                return findMember.getId();
            }
        }
        throw new IllegalStateException("아이디 또는 비밀번호가 맞지 않습니다.");
    }

    @Override
    public boolean checkLoginIdDuplication(String loginId) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        return optionalMember.isEmpty();

    }

    @Override
    @Transactional
    public void withdrawal(String loginId, String password) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if (optionalMember.isPresent()) {
            Member findMember = optionalMember.get();
            if (password.equals(findMember.getPassword())) {
                memberRepository.delete(findMember);
                return;
            }
        }
        throw new IllegalStateException("아이디 또는 비밀번호 불일치");
    }

    @Override
    @Transactional
    public void modifyAlias(Long memberId, String alias) {
        Member findMember = memberRepository.getById(memberId);
        findMember.aliasModify(alias);
    }
}
