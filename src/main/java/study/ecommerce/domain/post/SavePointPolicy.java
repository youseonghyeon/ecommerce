package study.ecommerce.domain.post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavePointPolicy {

    private final float saveRate;

    public int pointPolicy(int price) {
        float resultPrice = price - price * saveRate / 100;
        return (int) resultPrice;
    }
}
