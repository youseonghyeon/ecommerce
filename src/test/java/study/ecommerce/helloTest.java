package study.ecommerce;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.ecommerce.entity.Member;
import study.ecommerce.entity.QMember;
import study.ecommerce.security.SHA256Util;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
public class helloTest {
    @Autowired
    EntityManager em;
    @Autowired
    JPAQueryFactory queryFactory;

//    @Test
//    void hello() {
//        Member member = new Member("a", "b", "c", "d", "e");
//        em.persist(member);
//        em.flush();
//        em.clear();
//
//        List<Member> fetch = queryFactory.selectFrom(QMember.member).fetch();
//        for (Member f : fetch) {
//            Long id = f.getId();
//            String loginId = f.getLoginId();
//            System.out.println("id=" + id + " 로그인 아이디=" + loginId);
//        }
//    }

    @Test
    void password() {
        String salt = SHA256Util.generateSalt();
        String p1 = SHA256Util.getEncrypt("pass", salt);
        String p2 = SHA256Util.getEncrypt("pass", salt);

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
    }
}
