package com.example.corpu.sessionalStaff;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class SessionalStaffServiceImpl implements SessionalStaffService{
    private final SessionalStaffRepository sessionalStaffRepository;
    private final SessionalStaffMapper sessionalStaffMapper;

    public SessionalStaffServiceImpl(SessionalStaffRepository sessionalStaffRepository, SessionalStaffMapper sessionalStaffMapper) {
        this.sessionalStaffRepository = sessionalStaffRepository;
        this.sessionalStaffMapper = sessionalStaffMapper;
    }

    @Override
    public List<SessionalStaffDTO> getAll(String keyword) {
        return null;
    }

    @Override
    public Page<SessionalStaffDTO> getAllPaging(String keyword, Pageable pageable) {

        return null;
    }


//    @Override
//    public SessionalStaffDTO update(SessionalStaffDTO sessionalStaffDTO) {
//        Optional<SessionalStaff> currentSessionalStaffOpt = sessionalStaffRepository.findFirstByEmail(sessionalStaffDTO.getEmail());
//
//        SessionalStaff sessionalStaff = null;
//        //Case create
//        if (currentSessionalStaffOpt.isEmpty()){
//            sessionalStaff = sessionalStaffMapper.toEntity(sessionalStaffDTO);
//        }else{
//            //case update
//            sessionalStaff = currentSessionalStaffOpt.get();
//            if (!Objects.isNull(sessionalStaffDTO.getAvailability())){
//                sessionalStaff.setAvailability(sessionalStaffDTO.getAvailability());
//            }
////            if(!Objects.isNull(sessionalStaffDTO.getHourlyRate())){
////                sessionalStaff.setHourlyRate(sessionalStaffDTO.getHourlyRate());
////            }
//        }
//        SessionalStaff result = sessionalStaffRepository.save(sessionalStaff);
//
//        return sessionalStaffMapper.toDto(result);
//    }

}
