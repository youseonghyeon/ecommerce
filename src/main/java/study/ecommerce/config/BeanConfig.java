package study.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.ecommerce.domain.discount.DiscountPolicy;
import study.ecommerce.domain.discount.RateDiscountPolicy;
import study.ecommerce.domain.post.SavePointPolicy;

@Configuration
public class BeanConfig {

    private final float DISCOUNT_RATE = 10;
    private final float SAVE_POINT_RATE = 1;

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy(DISCOUNT_RATE);
//        return new NoDiscountPolicy();
    }

    @Bean
    public SavePointPolicy savePointPolicy() {
        return new SavePointPolicy(SAVE_POINT_RATE);
    }


}
