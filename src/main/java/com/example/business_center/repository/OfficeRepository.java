package com.example.business_center.repository;

import com.example.business_center.model.entity.Office;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    List<Office> findByIsEmptyTrue();

    @Query(value = """
                    select o
                    from Office o join Rent r on o.id = r.office.id
                    where r.client.username = :username
                    order by r.startDate
                    """)
    List<Office> findByClientUsername(String username);
    
    boolean existsByIdAndIsEmptyTrue(Long id);

    @Query(value = """
                    select o
                    from Office o join Rent r on o.id = r.office.id
                    where r.id = :id
                    """)
    Optional<Office> findByRentId(Long id);

    @Query("delete from Office o where o.id = :id")
    @Modifying
    void deleteById(@NonNull Long id);
}
