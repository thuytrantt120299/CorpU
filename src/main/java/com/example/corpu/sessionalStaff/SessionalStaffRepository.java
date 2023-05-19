package com.example.corpu.sessionalStaff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionalStaffRepository extends JpaRepository<SessionalStaff, String>{
    Optional<SessionalStaff> findFirstByEmail(String email);
    @Query("SELECT m FROM SessionalStaff m WHERE unitCount >= 0 ORDER BY m.firstName ASC")
    List<SessionalStaff> findAllByUnitCount();
    @Query("SELECT m FROM SessionalStaff m WHERE unitCount >= 0 ORDER BY m.firstName ASC")
    Page<SessionalStaff> findAllByUnitCount(Pageable pageable);
}
