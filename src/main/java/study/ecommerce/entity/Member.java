package study.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private int point; // 적립금

    @Enumerated(EnumType.STRING)
    private MemberShip memberShip;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders;

    public Member(String loginId, String password, String name, String mobile, String email) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        point = 0;
        memberShip = MemberShip.SILVER;
    }

    public void passwordModify(String password) {
        this.password = password;
    }

    public void mobileModify(String mobile) {
        this.mobile = mobile;
    }

    public void pointAdd(int point) {
        this.point += point;
    }

}
