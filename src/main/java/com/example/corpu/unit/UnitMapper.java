package com.example.corpu.unit;

import com.example.corpu.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",uses={})

public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {
    @Override
    @Named("id")
    UnitDTO toDto(Unit entity);
}
