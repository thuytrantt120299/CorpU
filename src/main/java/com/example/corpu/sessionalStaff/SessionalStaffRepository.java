package com.example.corpu.sessionalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionalStaffRepository extends JpaRepository<SessionalStaff, String>{
    Optional<SessionalStaff> findFirstByEmail(String email);

}
