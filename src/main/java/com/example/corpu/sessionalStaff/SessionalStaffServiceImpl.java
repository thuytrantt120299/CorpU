package com.example.corpu.sessionalStaff;

import com.example.corpu.constants.ErrorConstant;
import com.example.corpu.error.ValidationException;
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


    @Override
    public SessionalStaffDTO update(SessionalStaffDTO sessionalStaffDTO) {
        Optional<SessionalStaff> sessionalStaffOpt = sessionalStaffRepository.findFirstByEmail(sessionalStaffDTO.getEmail());

        if (sessionalStaffOpt.isEmpty()){
            throw new ValidationException(ErrorConstant.NOT_FOUND_OBJECT, String.format(ErrorConstant.NOT_FOUND_OBJECT_LABEL,"SessionalStaff"));
        }
        SessionalStaff sessionalStaff = sessionalStaffOpt.get();

        if (!Objects.isNull(sessionalStaffDTO.getAvailability())){
            sessionalStaff.setAvailability(sessionalStaffDTO.getAvailability());
        }
        if (!Objects.isNull(sessionalStaffDTO.getPreference())){
            sessionalStaff.setPreference(sessionalStaffDTO.getPreference());
        }
        SessionalStaff result = sessionalStaffRepository.save(sessionalStaff);
        return sessionalStaffMapper.toDto(result);
    }

}
