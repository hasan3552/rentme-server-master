package uz.unicorn.rentme.mapper.base;

import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @param <E>  -> entity
 * @param <D>  -> dto
 * @param <CD> -> create dto
 * @param <UD> -> update dto
 */
public interface GenericMapper<E, D, CD, UD> extends BaseMapper {

    E fromDTO(D dto);

    List<E> fromDTO(List<D> dtoList);

    D toDTO(E entity);

    List<D> toDTO(List<E> entities);

    E fromUpdateDTO(UD dto, @MappingTarget E entity);

    E fromCreateDTO(CD dto);

}
