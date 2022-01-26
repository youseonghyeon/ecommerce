package study.ecommerce.service;

import study.ecommerce.entity.OrderItem;

public interface OrderService {
    // 주문
    int order(Long memberId, OrderItem... orderItems);
    // 주문 조회
    // 주문 취소
}
