package com.example.corpu.unit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UnitService {
    List<UnitDTO> getAll(String keyword);
    Page<UnitDTO> getAllPaging(String keyword, Pageable pageable);
    UnitDTO add(UnitDTO unitDTO);
    UnitDTO update(UnitDTO unitDTO);
    boolean delete(String id);

}
