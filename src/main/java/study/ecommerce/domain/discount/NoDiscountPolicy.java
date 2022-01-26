package study.ecommerce.domain.discount;

public class NoDiscountPolicy implements DiscountPolicy{

    @Override
    public int discountPolicy(int price) {
        return price;
    }
}
