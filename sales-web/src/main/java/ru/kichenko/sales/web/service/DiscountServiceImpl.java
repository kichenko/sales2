/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kichenko.sales.data.dao.DiscountDao;
import ru.kichenko.sales.model.Discount;
import ru.kichenko.sales.web.dto.DiscountDto;
import ru.kichenko.sales.web.dto.DiscountShortDto;
import ru.kichenko.sales.web.dto.ProductDto;
import ru.kichenko.sales.web.service.core.ServiceException;

/**
 * Сервис скидочных предложений
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Slf4j
@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountDao discountDao;
    
    /**
     * Постраничный список скидочных предложений
     *
     * @param pageable страница
     * @return список скидочных предложений
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiscountDto> list(Pageable pageable) {
        Page<Discount> page = discountDao.findAll(pageable);
        return new PageImpl<>(Lists.transform(page.getContent(), new Function<Discount, DiscountDto>() {
            @Override
            public DiscountDto apply(Discount p) {
                return new DiscountDto(p.getId(), new ProductDto(p.getProduct().getName(), p.getProduct().getPrice()), p.getPercent(), p.getDate());
            }
        }), pageable, page.getTotalElements());

    }

    @Override
    public List<DiscountShortDto> shortList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<DiscountDto> list() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DiscountDto getById(Long id) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public DiscountDto insert(DiscountDto dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(DiscountDto dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
