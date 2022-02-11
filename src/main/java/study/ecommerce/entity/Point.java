package study.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id
    @GeneratedValue
    @Column(name = "point_id")
    private Long id;
    private LocalDateTime earnedDate;
    private PointCategory category;
    private int point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Point(PointCategory category, int point, Member member) {
        earnedDate = LocalDateTime.now();
        this.category = category;
        this.point = point;
        this.member = member;
    }
}
