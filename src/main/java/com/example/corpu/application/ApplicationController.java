package com.example.corpu.application;

import com.example.corpu.constants.ErrorConstant;
import com.example.corpu.constants.ValidateConstants;
import com.example.corpu.error.ValidationException;
import com.example.corpu.response.ApiResponse;
import com.example.corpu.response.DataPagingResponse;
import com.example.corpu.unit.UnitDTO;
import com.example.corpu.utils.ValidateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RequestMapping("/api/v1")
public class ApplicationController {
    private static final String[] listSort = {"unitName"};
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @RequestMapping(value ="/application", method = RequestMethod.GET)
    public ApiResponse<?> createApplication(){

        return new ApiResponse<>(200,null,null,null);
    }

    @RequestMapping(value = "/application", method = RequestMethod.POST)
    public ApiResponse<?> createApplication(@RequestBody ApplicationRequest applicationRequest, Authentication authentication){
        ApplicationDTO result;
        String staffName = authentication.getName();
        log.debug("REST request to create application");
        applicationRequest.setId(UUID.randomUUID().toString());
        try{
            result= applicationService.add(applicationRequest, staffName);
            if (Objects.isNull(result)||Objects.isNull(result.getId())){
                return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
            }
        } catch (ValidationException e) {
            return new ApiResponse<>(400, null, e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }

        return new ApiResponse<>(200, result, null, null);
    }

    @RequestMapping(value="/applications", method = RequestMethod.GET)
    public ApiResponse<?> getListApplication(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", required = false, defaultValue = ValidateConstants.PAGE) int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = ValidateConstants.PER_PAGE) int pageSize
    ) {
        log.debug("REST request to get a page of application");
        DataPagingResponse<ApplicationDTO> dataPagingResponse;
        String keywordOpt = Optional.ofNullable(keyword).orElse("");

        if (keywordOpt.length() > ValidateConstants.FIELD_LENGTH_LIMIT_NORMAL) {
            return new ApiResponse<>(400, null, ErrorConstant.OVER_LENGTH_FIELD
                    , String.format(ErrorConstant.OVER_LENGTH_FIELD_LABEL, "keyword", ValidateConstants.FIELD_LENGTH_LIMIT_NORMAL));
        }
        try{
            if (page == -1 || pageSize == -1) {
                List<ApplicationDTO> applicationDTOList = applicationService.getAll(keywordOpt);
                dataPagingResponse = new DataPagingResponse<>(applicationDTOList, applicationDTOList.size(), -1,-1);
            }else {
                Page<ApplicationDTO> data = applicationService.getAllPaging(keywordOpt, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, listSort)));
                dataPagingResponse = new DataPagingResponse<>(data.getContent(), data.getTotalElements(), data.getNumber(), data.getSize());
            }
        }catch (Exception e){
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }
        return new ApiResponse<>(200, dataPagingResponse, null, null);
    }

    @RequestMapping(value = "/application/{id}", method = RequestMethod.PATCH)
    public ApiResponse<?> updateApplication(
            @PathVariable(value = "id", required = true)final String id,
            @RequestBody Integer status){
        log.debug("REST request to update application: {}",status);
        String result;
        try {
            result = applicationService.update(id, status);
        } catch (ValidationException e) {
            return new ApiResponse<>(400, null, e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }
        return new ApiResponse<>(200, result, null, null);
    }

}
