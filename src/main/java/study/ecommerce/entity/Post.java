package study.ecommerce.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String author;
    private String title;
    private String content;
    private float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    public Post(String author, String title, String content, float rating) {
        this(author, title, content, rating, null);
    }

    public Post(String author, String title, String content, float rating, Item item) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.rating = rating;
        if (item != null) {
            this.item = item;
            this.item.getPosts().add(this);
        }
    }

    public void postModify(String title, String content, float rating) {
        this.title = title;
        this.content = content;
        this.rating = rating;
    }

}
