package com.example.business_center.repository;

import com.example.business_center.model.entity.Rent;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    List<Rent> findAllByClientUsername(String username);

    @Query(value = """
        delete from Rent r
        where r.id = :id
        """)
    @Modifying
    void deleteById(@NonNull Long id);
}
