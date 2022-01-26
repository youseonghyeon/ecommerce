package study.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.Member;
import study.ecommerce.entity.OrderItem;
import study.ecommerce.entity.Orders;
import study.ecommerce.repository.MemberRepository;
import study.ecommerce.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public int order(Long memberId, OrderItem... orderItems) {
        List<Orders> orderList = new ArrayList<>();
        Member member = searchMember(memberId);
        LocalDateTime now = LocalDateTime.now();

        for (OrderItem orderItem : orderItems) {
            Orders newOrder = new Orders(orderItem.getId(), now, member);
            orderList.add(newOrder);
        }
        orderRepository.saveAll(orderList);
        return orderList.size(); //주문된 item 의 종류 개수 바환(quantity 반영X)
    }

    private Member searchMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new IllegalStateException("해당 회원이 존재하지 않습니다.");
        }
        return optionalMember.get();
    }
}
