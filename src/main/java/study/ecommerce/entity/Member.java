package study.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.ecommerce.security.SHA256Util;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String loginId;
    private String password;
    private String name; // 이름
    private String mobile; // 핸드폰 번호
    private String email; // 이메일
    private int curPoint; // 적립금
    private String alias; // 별명
    private String salt;

    @Enumerated(EnumType.STRING)
    private MemberShip memberShip;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders;

    public Member(String loginId, String password, String name, String mobile, String email) {
        salt = SHA256Util.generateSalt();
        this.loginId = loginId;
        this.password = SHA256Util.getEncrypt(password, salt);
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        curPoint = 0;
        memberShip = MemberShip.SILVER;
        alias = name;
    }

    public void passwordModify(String password) {
        this.password = password;
    }

    public void mobileModify(String mobile) {
        this.mobile = mobile;
    }

    public void pointAdd(int point) {
        this.curPoint += point;
    }

    public void aliasModify(String alias) {
        this.alias = alias;
    }

}
