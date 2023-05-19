package com.example.corpu.application;

import com.example.corpu.sessionalStaff.SessionalStaff;
import com.example.corpu.unit.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    Optional<Application> findFirstBySessionalStaffAndUnit(SessionalStaff sessionalStaff, Unit unit);

    @Query("SELECT m FROM Application m WHERE status = 0 AND (lower(m.applicantEmail) LIKE %:keyword% OR " +
            "lower(m.unitName) LIKE %:keyword%) ORDER BY m.applicationDate ASC")
    List<Application> getApplications(@Param("keyword") String keyword);
    @Query("SELECT m FROM Application m WHERE status = 0 AND (lower(m.applicantEmail) LIKE %:keyword% OR " +
            "lower(m.unitName) LIKE %:keyword%) ORDER BY m.applicationDate ASC")
    Page<Application> getApplications(@Param("keyword") String keyword, Pageable pageable);
}
