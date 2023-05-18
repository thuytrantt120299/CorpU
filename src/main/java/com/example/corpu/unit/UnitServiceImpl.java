package com.example.corpu.unit;
import com.example.corpu.constants.ErrorConstant;
import com.example.corpu.error.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
public class UnitServiceImpl implements UnitService{

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    @Override
    public List<UnitDTO> getAll(String keyword) {
        log.debug("Request to get all units");
        List<Unit> result = unitRepository.getUnits(keyword.toLowerCase());
        List<UnitDTO> dtos = unitMapper.toDto(result);
        return dtos;
    }
    @Override
    public Page<UnitDTO> getAllPaging(String keyword, Pageable pageable) {

        log.debug("Request to get all banners");
        Page<Unit> units = unitRepository.getUnits(keyword.toLowerCase(),pageable);
        Page<UnitDTO> result = units.map(unitMapper::toDto);

        return result;
    }

    @Override
    public UnitDTO add(UnitDTO unitDTO) {
        if (Objects.isNull(unitDTO.getName())){
            throw new ValidationException(ErrorConstant.BLANK_FIELD, String.format(ErrorConstant.BLANK_FIELD_LABEL, "Unit name"));
        }
        if (Objects.isNull(unitDTO.getCode())){
            throw new ValidationException(ErrorConstant.BLANK_FIELD, String.format(ErrorConstant.BLANK_FIELD_LABEL, "Unit code"));
        }
        List<Unit> checkName = unitRepository.findAllByCode(unitDTO.getCode());
        if (!checkName.isEmpty()){
            throw new ValidationException(ErrorConstant.EXIST_OBJECT, String.format(ErrorConstant.EXISTED_OBJECT_LABEL, ErrorConstant.UNIT_PREFIX_NAME));
        }
        Unit unit = unitMapper.toEntity(unitDTO);
        Unit result = unitRepository.save(unit);
        return unitMapper.toDto(result);
    }

    @Override
    public UnitDTO update(UnitDTO unitDTO) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
