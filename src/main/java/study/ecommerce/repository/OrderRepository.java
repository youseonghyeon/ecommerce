package study.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.ecommerce.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
}
