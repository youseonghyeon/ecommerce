package study.ecommerce.domain.discount;

public class RateDiscountPolicy implements DiscountPolicy {

    private final float DISCOUNT_RATE = 10;

    @Override
    public int discountPolicy(int price) {
        float resultPrice = price - price * DISCOUNT_RATE / 100;
        return (int) resultPrice;
    }
}
