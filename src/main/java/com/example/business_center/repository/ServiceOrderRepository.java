package com.example.business_center.repository;

import com.example.business_center.model.entity.ServiceOrder;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    List<ServiceOrder> findAllByClientUsername(String username);

    @Query(value = """
        delete from ServiceOrder so
        where so.id = :id
        """)
    @Modifying
    void deleteById(@NonNull Long id);
}
