package uz.unicorn.rentme.service.base;

import uz.unicorn.rentme.criteria.base.BaseCriteria;
import uz.unicorn.rentme.dto.base.BaseDTO;
import uz.unicorn.rentme.dto.base.GenericDTO;
import uz.unicorn.rentme.response.DataDTO;
import uz.unicorn.rentme.response.ResponseEntity;

/**
 * @param <D> -> dto
 * @param <CD> -> create dto
 * @param <UD> -> update dto
 * @param <C> -> criteria
 */
public interface GenericCrudService<
        D extends GenericDTO,
        CD extends BaseDTO,
        UD extends GenericDTO,
        C extends BaseCriteria> extends GenericService<D, C> {

    ResponseEntity<DataDTO<Long>> create(CD dto);

    ResponseEntity<DataDTO<Long>> update(UD dto);

    ResponseEntity<DataDTO<Boolean>> delete(Long id);

}
