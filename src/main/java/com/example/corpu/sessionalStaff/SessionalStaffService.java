package com.example.corpu.sessionalStaff;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;


public interface SessionalStaffService {

    List<SessionalStaffDTO> getAll(String keyword);


    /**
     *
     * @param pageable
     * @return the persisted list entity.
     */
    Page<SessionalStaffDTO> getAllPaging(String keyword, Pageable pageable);


    /**
     * Save address unit.
     *
     * @param sessionalStaffDTO the list entity to save.
     * @return the persisted entity.
     */
    SessionalStaffDTO update(SessionalStaffDTO sessionalStaffDTO);



}
