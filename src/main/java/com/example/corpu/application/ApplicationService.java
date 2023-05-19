package com.example.corpu.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationService {
    List<ApplicationDTO> getAll(String keyword);
    Page<ApplicationDTO> getAllPaging(String keyword, Pageable pageable);
    ApplicationDTO add(ApplicationRequest applicationRequest, String sessionalStaffName);
    String update(String id, Integer status);
    boolean delete(String id);
}
