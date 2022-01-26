package study.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private int sales; //판매량
    private Integer shippingCost;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "item")
    private List<Post> posts;

    public Item(String name, int price, int quantity) {
        this(name, price, quantity, 0);
    }

    public Item(String name, int price, int quantity, Integer shippingCost) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shippingCost = shippingCost;
        sales = 0;
    }

    public void nameModify(String name) {
        this.name = name;
    }

    public void priceModify(int price) {
        this.price = price;
    }
}
