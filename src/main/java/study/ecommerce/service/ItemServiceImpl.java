package study.ecommerce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.ecommerce.entity.Item;
import study.ecommerce.repository.ItemRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Long enroll(String name, int price, int quantity) {
        Item item = new Item(name, price, quantity);
        itemRepository.save(item);
        return item.getId();
    }

    @Override
    public Pageable search() {
        // Pageable 구현 TODO
        return null;
    }

    @Override
    public boolean quantityCheck(Long itemId, int quantity) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item findItem = optionalItem.get();
            return findItem.getQuantity() >= quantity;
        } else {
            throw new IllegalStateException("해당 상품이 존재하지 않습니다.");
        }
    }
}
