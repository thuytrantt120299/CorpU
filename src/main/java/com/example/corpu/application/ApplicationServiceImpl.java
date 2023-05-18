package com.example.corpu.application;

import com.example.corpu.constants.ErrorConstant;
import com.example.corpu.error.ValidationException;
import com.example.corpu.sessionalStaff.SessionalStaff;
import com.example.corpu.sessionalStaff.SessionalStaffRepository;
import com.example.corpu.unit.Unit;
import com.example.corpu.unit.UnitDTO;
import com.example.corpu.unit.UnitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final SessionalStaffRepository sessionalStaffRepository;
    private final UnitRepository unitRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public List<ApplicationDTO> getAll(String keyword) {
        log.debug("Request to get all applications");

        List<Application> result = applicationRepository.getApplications(keyword.toLowerCase());
        List<ApplicationDTO> applicationDTOList = result.stream()
                .map(a -> ApplicationDTO.builder()
                        .id(a.getId())
                        .applicationName(a.getSessionalStaff().getFirstName().concat(" " + a.getSessionalStaff().getLastName()))
                        .availability(a.getSessionalStaff().getAvailability())
                        .unitName(a.getUnitName())
                        .preferences(a.getSessionalStaff().getPreferences())
                        .phone(a.getSessionalStaff().getPhone())
                        .email(a.getSessionalStaff().getEmail())
                        .build())
                .collect(Collectors.toList());
        return applicationDTOList;
}

    @Override
    public Page<ApplicationDTO> getAllPaging(String keyword, Pageable pageable) {
        log.debug("Request to get all applications with paging");

        Page<Application> result = applicationRepository.getApplications(keyword.toLowerCase(), pageable);
        Page<ApplicationDTO> applicationDTOList = result.map(a -> ApplicationDTO.builder()
                .id(a.getId())
                .applicationName(a.getSessionalStaff().getFirstName().concat(" " + a.getSessionalStaff().getLastName()))
                .availability(a.getSessionalStaff().getAvailability())
                .unitName(a.getUnitName())
                .preferences(a.getSessionalStaff().getPreferences())
                .phone(a.getSessionalStaff().getPhone())
                .email(a.getSessionalStaff().getEmail())
                .build());
        return applicationDTOList;
    }


    @Override
    public ApplicationDTO add(ApplicationRequest applicationRequest, String sessionalStaffName) {
        //check if sessional staff exist
        Optional<SessionalStaff> sessionalStaffOpt = sessionalStaffRepository.findFirstByEmail(sessionalStaffName);
        if (!sessionalStaffOpt.isPresent()){
            throw new ValidationException(ErrorConstant.NOT_FOUND_OBJECT, String.format(ErrorConstant.NOT_FOUND_OBJECT_LABEL,"SessionalStaff"));
        }
        //check if sessional unit exist
        Optional<Unit> unitOpt = unitRepository.findById(applicationRequest.getUnitId());
        if (!unitOpt.isPresent()){
            throw new ValidationException(ErrorConstant.NOT_FOUND_OBJECT, String.format(ErrorConstant.NOT_FOUND_OBJECT_LABEL,"Unit"));
        }
        //check if application exist
        Optional<Application> applicationOpt = applicationRepository.findFirstBySessionalStaffAndUnit(sessionalStaffOpt.get(),unitOpt.get());
        if (applicationOpt.isPresent()){
            throw new ValidationException(ErrorConstant.EXIST_OBJECT, String.format(ErrorConstant.EXISTED_OBJECT_LABEL,"Application"));
        }

        Application application = Application.builder()
                .id(applicationRequest.getId())
                .sessionalStaff(sessionalStaffOpt.get())
                .unit(unitOpt.get())
                .applicantEmail(sessionalStaffOpt.get().getEmail())
                .unitName(unitOpt.get().getName())
                .status(0)
                .applicationDate(Instant.now())
                .build();
        log.debug(application.toString());
       Application result = applicationRepository.save(application);
    log.debug(result.toString());
        return ApplicationDTO.builder()
                .id(result.getId())
                .applicationName(result.getSessionalStaff().getFirstName().concat(" " + result.getSessionalStaff().getLastName()))
                .availability(result.getSessionalStaff().getAvailability())
                .unitName(result.getUnit().getName())
                .preferences(result.getSessionalStaff().getPreferences())
                .phone(result.getSessionalStaff().getPhone())
                .email(result.getSessionalStaff().getEmail())
                .build();
    }

    @Override
    public ApplicationDTO update(ApplicationDTO applicationDTO) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
