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
    @Column(name = "cr_by", updatable = false)
    private String createdBy;
    
    @LastModifiedBy
    @Column(name = "mo_by")
    private String lastModifiedBy;

}
