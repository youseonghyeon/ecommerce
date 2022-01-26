package study.ecommerce.service;

import org.springframework.data.domain.Pageable;

public interface ItemService {
    // 상품 등록/조회
    Long enroll(String name, int price, int quantity);
    Pageable search();

    // 수량 확인(주문이 가능한지)
    boolean quantityCheck(Long itemId, int quantity);

}
