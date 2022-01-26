package study.ecommerce.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseTimeEntity {

    @CreatedDate
    @Column(name = "cr_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "mo_date")
    private LocalDateTime lastModifiedDate;
}
