package study.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    private LocalDateTime orderDate;
    // + 배송
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public Orders(Long id, LocalDateTime orderDate, Member member) {
        this.id = id;
        this.orderDate = orderDate;
        this.member = member;
        member.getOrders().add(this);
        status = OrderStatus.ORDER;

    }

    public void statusChange(OrderStatus status) {
        this.status = status;
    }
}
