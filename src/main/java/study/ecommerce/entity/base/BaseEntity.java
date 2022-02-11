package study.ecommerce.entity.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public class BaseEntity {

    @CreatedBy
    @Column(name = "create_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modify_by")
    private String lastModifiedBy;

}
