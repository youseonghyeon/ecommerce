package study.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.ecommerce.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
