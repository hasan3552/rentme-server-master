package uz.unicorn.rentme.service.base;

import lombok.RequiredArgsConstructor;
import uz.unicorn.rentme.mapper.base.BaseMapper;
import uz.unicorn.rentme.repository.base.BaseRepository;

@RequiredArgsConstructor
public class AbstractService<M extends BaseMapper, R extends BaseRepository> implements BaseService {

    protected final M mapper;
    protected final R repository;

}
