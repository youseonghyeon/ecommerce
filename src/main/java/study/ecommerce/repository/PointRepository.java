package study.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.ecommerce.entity.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
}
