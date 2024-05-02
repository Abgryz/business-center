package com.example.business_center.repository;

import com.example.business_center.model.entity.Service;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("""
            select s
            from Service s join ServiceOrder so on s.id = so.service.id
            where so.client.username = :username
            order by so.date
            """)
    List<Service> findByClientUsername(String username);

    @Modifying
    @Query("delete from Service s where s.id = :id")
    void deleteById(@NonNull Long id);
}
