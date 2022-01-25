package study.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.ecommerce.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
