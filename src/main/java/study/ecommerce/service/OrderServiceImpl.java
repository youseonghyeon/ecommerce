package study.ecommerce.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.*;
import study.ecommerce.repository.MemberRepository;
import study.ecommerce.repository.OrderItemRepository;
import study.ecommerce.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.*;

import static study.ecommerce.entity.QOrders.orders;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public Long order(Long memberId, List<OrderItem> orderItems) {
        Member findMember = searchMember(memberId);
        Orders newOrder = new Orders(LocalDateTime.now(), findMember);
        // 주문 객체 생성
        orderRepository.save(newOrder);

        for (OrderItem orderItem : orderItems) {
            // OrderItem 와 Order 의 릴레이션 부여
            orderItem.orderInput(newOrder);
        }
        orderItemRepository.saveAll(orderItems); // orderItem을 어디에서 영속화할지 정해야함 TODO

        return newOrder.getId();
    }

    @Override
    @Transactional
    public void rejectByManager(Long orderId) {
        Orders order = searchOrder(orderId);
        order.statusChange(OrderStatus.REJECT);
    }

    @Override
    @Transactional
    public void cancelByClient(Long orderId, Long memberId) {
        Orders findOrder = searchOrder(orderId);
        List<Long> fetch = queryFactory
                .select(orders.member.id)
                .from(orders)
                .where(orders.id.eq(orderId))
                .fetch();
        if (!fetch.get(0).equals(memberId)) {
            throw new IllegalStateException("주문 접근 권한이 없습니다.");
        }
        findOrder.statusChange(OrderStatus.CANCEL);
    }

    @Override
    @Transactional
    public void statusChange(Long orderId, OrderStatus status) {
        Orders orders = searchOrder(orderId);
        orders.statusChange(status);
    }

    private Member searchMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new IllegalStateException("해당 회원이 존재하지 않습니다.");
        }
        return optionalMember.get();
    }

    private Orders searchOrder(Long orderId) {
        Optional<Orders> optionalOrders = orderRepository.findById(orderId);
        if (optionalOrders.isEmpty()) {
            throw new IllegalStateException("해당 주문이 존재하지 않습니다.");
        }
        return optionalOrders.get();
    }
}
