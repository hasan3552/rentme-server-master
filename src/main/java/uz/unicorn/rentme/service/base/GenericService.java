package uz.unicorn.rentme.service.base;

import uz.unicorn.rentme.criteria.base.BaseCriteria;
import uz.unicorn.rentme.dto.base.GenericDTO;
import uz.unicorn.rentme.response.DataDTO;
import uz.unicorn.rentme.response.ResponseEntity;

import java.util.List;

/**
 * @param <D> -> dto
 * @param <C> -> create dto
 */
public interface GenericService<D extends GenericDTO, C extends BaseCriteria> {

    ResponseEntity<DataDTO<D>> get(Long id);

    ResponseEntity<DataDTO<List<D>>> getAll(C criteria);


}
