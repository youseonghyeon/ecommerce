package study.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.ecommerce.domain.discount.DiscountPolicy;
import study.ecommerce.domain.discount.RateDiscountPolicy;
import study.ecommerce.domain.order.OrderSavePoint;
import study.ecommerce.domain.post.PostSavePoint;

@Configuration
public class DiscountSaveConfig {
    private final float DISCOUNT_RATE = 10;
    private final float ORDER_POINT_RATE = 3;
    private final float POST_POINT_RATE = 1;

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy(DISCOUNT_RATE);
//        return new NoDiscountPolicy();
    }

    @Bean
    public PostSavePoint postSavePoint() {
        return new PostSavePoint(POST_POINT_RATE);
    }

    @Bean
    public OrderSavePoint orderSavePoint() {
        return new OrderSavePoint(ORDER_POINT_RATE);
    }
}
