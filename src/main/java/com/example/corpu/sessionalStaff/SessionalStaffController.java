package com.example.corpu.sessionalStaff;

import com.example.corpu.constants.ErrorConstant;
import com.example.corpu.constants.ValidateConstants;
import com.example.corpu.error.ValidationException;
import com.example.corpu.response.ApiResponse;
import com.example.corpu.response.DataPagingResponse;
import com.example.corpu.utils.ValidateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1")
public class SessionalStaffController {
    private static final String[] listSort = {"name"};

    private final SessionalStaffService sessionalStaffService;


    public SessionalStaffController(SessionalStaffService sessionalStaffService) {
        this.sessionalStaffService = sessionalStaffService;
    }

    @RequestMapping(value="/sessionalStaffs", method = RequestMethod.GET)
    public ApiResponse<?> getListSessionalStaff(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", required = false, defaultValue = ValidateConstants.PAGE) int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = ValidateConstants.PER_PAGE) int pageSize
    ) {
        log.debug("REST request to get a page of banner");
        DataPagingResponse<SessionalStaffDTO> dataPagingResponse;
        String keywordOpt = Optional.ofNullable(keyword).orElse("");

        if (keywordOpt.length() > ValidateConstants.FIELD_LENGTH_LIMIT_NORMAL) {
            return new ApiResponse<>(400, null, ErrorConstant.OVER_LENGTH_FIELD
                    , String.format(ErrorConstant.OVER_LENGTH_FIELD_LABEL, "keyword", ValidateConstants.FIELD_LENGTH_LIMIT_NORMAL));
        }

        try{
            if (page == -1 || pageSize == -1) {
                List<SessionalStaffDTO> sessionalStaffDTOList = sessionalStaffService.getAll(keywordOpt);
                dataPagingResponse = new DataPagingResponse<>(sessionalStaffDTOList, sessionalStaffDTOList.size(), -1,-1);
            }else {
                Page<SessionalStaffDTO> data = sessionalStaffService.getAllPaging(keywordOpt, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, listSort)));
                dataPagingResponse = new DataPagingResponse<>(data.getContent(), data.getTotalElements(), data.getNumber(), data.getSize());
            }
        }catch (Exception e){
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);

        }

        return new ApiResponse<>(200, dataPagingResponse, null, null);
    }

    @RequestMapping(value = "/sessionalStaff", method = RequestMethod.PATCH)
    public ApiResponse<?> updateSessionalStaff(
            Authentication authentication,
            @RequestBody SessionalStaffDTO sessionalStaffDTO){
        log.debug("REST request to update sessional staff: {}",sessionalStaffDTO);
        SessionalStaffDTO result;
        sessionalStaffDTO.setEmail(authentication.getName());
        try {
            result = sessionalStaffService.update(sessionalStaffDTO);
        } catch (ValidationException e) {
            return new ApiResponse<>(400, null, e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }
        return new ApiResponse<>(200, result, null, null);
    }
}
