package study.ecommerce.domain.discount;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RateDiscountPolicy implements DiscountPolicy {

    private final float discountRate;

    @Override
    public int discountPolicy(int price) {
        float resultPrice = price - price * discountRate / 100;
        return (int) resultPrice;
    }
}
