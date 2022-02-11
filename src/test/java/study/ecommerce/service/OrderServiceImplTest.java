package study.ecommerce.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.*;
import study.ecommerce.repository.ItemRepository;
import study.ecommerce.repository.MemberRepository;
import study.ecommerce.repository.OrderItemRepository;
import study.ecommerce.repository.OrderRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class OrderServiceImplTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    EntityManager em;
    private final static String TEST_ID = "test-i";
    private final static String TEST_PW = "test-p";
    private final static String TEST_NAME = "test-n";
    private final static String TEST_MOBILE = "test-m";
    private final static String TEST_EMAIL = "test-e";

    @Test
    @DisplayName("주문")
    void order() {
        Member member = createMember();
        List<OrderItem> container = new ArrayList<>();
        Item item1 = new Item("item1", 3000, 100);
        Item item2 = new Item("item2", 1000, 100);
        em.persist(item1);
        em.persist(item2);
        OrderItem orderItem1 = new OrderItem(3000, 1, item1);
        OrderItem orderItem2 = new OrderItem(2000, 2, item2);
        em.persist(orderItem1);
        em.persist(orderItem2);
        container.add(orderItem1);
        container.add(orderItem2);

        Long orderId = orderService.order(member.getId(), container);
        Optional<Orders> optionalOrders = orderRepository.findById(orderId);

        assertThat(orderItem1.getOrder().getId()).isEqualTo(orderId);
        assertThat(orderItem2.getOrder().getId()).isEqualTo(orderId);
        assertThat(optionalOrders).isPresent();

        deleteMember(member);
    }

    @Test
    @DisplayName("주문 거절")
    void rejectByManager() {
        //given
        Member member = createMember();
        Orders orders = new Orders(LocalDateTime.now(), member);
        em.persist(orders);

        //when
        orderService.rejectByManager(orders.getId());

        //then
        assertThat(orders.getStatus()).isEqualTo(OrderStatus.REJECT);
        deleteMember(member);
    }

    @Test
    @DisplayName("주문 취소")
    void cancelByClientSuccess() {
        //given
        Member member = createMember();
        Orders orders = new Orders(LocalDateTime.now(), member);
        em.persist(orders);

        //when
        orderService.cancelByClient(orders.getId(), member.getId());

        //then
        assertThat(orders.getStatus()).isEqualTo(OrderStatus.CANCEL);
        deleteMember(member);
    }

    @Test
    @DisplayName("주문 취소 실패(memberId 불일치/ 접근 권한 X)")
    void cancelByClientFail() {
        //given
        Member member = createMember();
        Orders orders = new Orders(LocalDateTime.now(), member);
        em.persist(orders);

        //when
        assertThatIllegalStateException()
                .isThrownBy(() -> orderService.cancelByClient(orders.getId(), 99L))
                .withMessage("주문 접근 권한이 없습니다.");
        deleteMember(member);
    }

    @Test
    void statusChange() {
        //given
        Member member = createMember();
        Orders orders = new Orders(LocalDateTime.now(), member);
        em.persist(orders);

        //when
        orderService.statusChange(orders.getId(), OrderStatus.PAYMENT);
        assertThat(orders.getStatus()).isEqualTo(OrderStatus.PAYMENT);
        deleteMember(member);
    }

    private Member createMember() {
        Member member = new Member(TEST_ID, TEST_PW, TEST_NAME, TEST_MOBILE, TEST_EMAIL);
        memberRepository.save(member);
        return member;
    }

    private void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
