package uz.unicorn.rentme.controller.base;

import lombok.RequiredArgsConstructor;
import uz.unicorn.rentme.service.base.BaseService;

@RequiredArgsConstructor
public class AbstractController<S extends BaseService> implements BaseController {

    protected final S service;

}
