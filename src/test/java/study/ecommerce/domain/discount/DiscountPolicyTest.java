package study.ecommerce.domain.discount;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DiscountPolicyTest {

    @Test
    @DisplayName("10% 할인 정책 적용")
    void discountPolicy() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestDomainConfig.class);
        DiscountPolicy dp = ac.getBean("discountPolicy", DiscountPolicy.class);

        Assertions.assertThat(dp.discountPolicy(20000)).isEqualTo(18000);
    }

    static class TestDomainConfig {
        @Bean
        public DiscountPolicy discountPolicy() {
            return new RateDiscountPolicy(10);
        }
    }
}
