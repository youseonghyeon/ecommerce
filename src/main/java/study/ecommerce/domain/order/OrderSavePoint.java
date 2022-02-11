package study.ecommerce.domain.order;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderSavePoint {

    private final float saveRate;

    public int pointPolicy(int price) {
        float resultPrice = price - price * saveRate / 100;
        return (int) resultPrice;
    }
}
