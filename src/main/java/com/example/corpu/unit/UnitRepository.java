package com.example.corpu.unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit,String> {

    List<Unit> findAllByCode(String code);
    @Query("SELECT m FROM Unit m WHERE lower(m.name) LIKE %:keyword% ORDER BY m.code ASC")
    List<Unit> getUnits(@Param("keyword") String keyword);
    @Query("SELECT m FROM Unit m WHERE lower(m.name) LIKE %:keyword% ORDER BY m.code ASC")
    Page<Unit> getUnits(@Param("keyword") String keyword, Pageable pageable);
}
