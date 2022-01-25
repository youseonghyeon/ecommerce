package study.ecommerce.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.ecommerce.domain.discount.DiscountPolicy;
import study.ecommerce.domain.discount.RateDiscountPolicy;

import javax.persistence.EntityManager;

@Configuration
public class BeanConfig {

    // 도메인 로직
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public JPAQueryFactory queryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
