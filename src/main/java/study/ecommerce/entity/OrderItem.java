package study.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    public void cancel() {
        //수량 원상복구
    }

    public OrderItem(int orderPrice, int count) {
        this(orderPrice, count, null, null);
    }

    public OrderItem(int orderPrice, int count, Orders order, Item item) {
        this.orderPrice = orderPrice;
        this.count = count;
        if (order != null) {
            this.order = order;
            order.getOrderItems().add(this);
        }
        if (item != null) {
            this.item = item;
            item.getOrderItems().add(this);
        }
    }
}
