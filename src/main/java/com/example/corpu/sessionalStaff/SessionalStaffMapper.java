package com.example.corpu.sessionalStaff;

import com.example.corpu.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface SessionalStaffMapper extends EntityMapper<SessionalStaffDTO, SessionalStaff> {
    @Override
    @Named("id")
    SessionalStaffDTO toDto(SessionalStaff entity);
}
