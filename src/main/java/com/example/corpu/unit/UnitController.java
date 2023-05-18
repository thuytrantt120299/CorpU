package com.example.corpu.unit;

import com.example.corpu.constants.ErrorConstant;
import com.example.corpu.constants.ValidateConstants;
import com.example.corpu.error.ValidationException;
import com.example.corpu.response.ApiResponse;
import com.example.corpu.response.DataPagingResponse;
import com.example.corpu.utils.ValidateUtils;
import lombok.extern.log4j.Log4j2;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1")
public class UnitController {

    private static final String[] listSort = {"name"};

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @RequestMapping(value="/units", method = RequestMethod.GET)
    public ApiResponse<?> getListUnit(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", required = false, defaultValue = ValidateConstants.PAGE) int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = ValidateConstants.PER_PAGE) int pageSize
    ) {
        log.debug("REST request to get a page of unit");
        DataPagingResponse<UnitDTO> dataPagingResponse;
        String keywordOpt = Optional.ofNullable(keyword).orElse("");

        if (keywordOpt.length() > ValidateConstants.FIELD_LENGTH_LIMIT_NORMAL) {
            return new ApiResponse<>(400, null, ErrorConstant.OVER_LENGTH_FIELD
                    , String.format(ErrorConstant.OVER_LENGTH_FIELD_LABEL, "keyword", ValidateConstants.FIELD_LENGTH_LIMIT_NORMAL));
        }
        try{
            if (page == -1 || pageSize == -1) {
                List<UnitDTO> unitDTOList = unitService.getAll(keywordOpt);
                dataPagingResponse = new DataPagingResponse<>(unitDTOList, unitDTOList.size(), -1,-1);
            }else {
                Page<UnitDTO> data = unitService.getAllPaging(keywordOpt, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, listSort)));
                dataPagingResponse = new DataPagingResponse<>(data.getContent(), data.getTotalElements(), data.getNumber(), data.getSize());
            }
        }catch (Exception e){
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }
        return new ApiResponse<>(200, dataPagingResponse, null, null);
    }
    @RequestMapping(value = "/unit", method = RequestMethod.POST)
    public ApiResponse<?> createBanner(@RequestBody UnitDTO unitDTO){
        UnitDTO result;
        log.debug("REST request to create unit");
        unitDTO.setId(UUID.randomUUID().toString());
        try{
            unitDTO = ValidateUtils.trimObject(unitDTO,UnitDTO.class);
            result=unitService.add(unitDTO);
            if (Objects.isNull(result)||Objects.isNull(result.getId())){
                return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
            }
        } catch (ValidationException e) {
            return new ApiResponse<>(400, null, e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            return new ApiResponse<>(400, null, ValidateConstants.ERROR, ValidateConstants.ERROR_LABEL);
        }

        return new ApiResponse<>(200, null, null, null);
    }
}
