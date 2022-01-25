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
    // 옵션(색상)

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "item")
    private List<Post> posts;

    public Item(Long id) {
        this.id = id;
    }
}
