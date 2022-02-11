package study.ecommerce.service;

import study.ecommerce.entity.OrderItem;
import study.ecommerce.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    // 주문
    Long order(Long memberId, List<OrderItem> orderItems);

    // 주문 조회
    // 주문 거절
    void rejectByManager(Long orderId);
    // 주문 취소
    void cancelByClient(Long orderId, Long memberId);
    // 주문 상태 변화
    void statusChange(Long orderId, OrderStatus status);
}
