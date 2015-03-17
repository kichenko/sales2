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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kichenko.sales.data.dao.ItemDao;
import ru.kichenko.sales.data.dao.ProductDao;
import ru.kichenko.sales.data.dao.SaleDao;
import ru.kichenko.sales.model.Item;
import ru.kichenko.sales.model.Product;
import ru.kichenko.sales.model.Sale;
import ru.kichenko.sales.web.dto.ItemDto;
import ru.kichenko.sales.web.dto.ItemShortDto;
import ru.kichenko.sales.web.dto.SpecialProductOfferDto;
import ru.kichenko.sales.web.service.cache.DiscountCacheService;
import ru.kichenko.sales.web.service.core.ServiceException;

/**
 * Сервис позиций заказа
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private DiscountCacheService discountCacheService;

    /**
     * Сохранение позиции заказа
     *
     * @param dto продукт dto
     * @return dto продукт dto
     * @throws ServiceException
     */
    @Override
    @Transactional
    public ItemDto insert(ItemDto dto) throws ServiceException {

        Sale sale;
        Item item;
        Product product;

        if (itemDao.findBySaleIdAndProductId(dto.getSaleId(), dto.getProductId()) != null) {
            throw new ServiceException("Невозможно добавить дублирующую позицию в заказ");
        }

        sale = saleDao.findOne(dto.getSaleId());
        product = productDao.findOne(dto.getProductId());

        if (sale != null && product != null) {
            item = new Item(sale, product, dto.getQuantity(), 0L);

            //установить скидку на товар, если есть...
            SpecialProductOfferDto offer = discountCacheService.getDiscountProductInfo();
            if (offer != null) {
                if (offer.getProduct().getId().equals(product.getId())) {
                    item.setDiscount(product.getPrice() / 100 * offer.getPercent());
                }
            }

            //сохраняем в БД
            itemDao.save(item);

            //обновлем dto
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setDiscount(item.getDiscount());
        } else {
            throw new ServiceException("Не найден продукт или продажа в БД");
        }

        return dto;
    }

    /**
     * Поиск позиций по #id заказа
     *
     * @param saleId #id заказа
     * @return список позиций заказа
     */
    @Override
    @Transactional(readOnly = true)
    public List<ItemDto> findBySaleId(Long saleId) {
        return Lists.transform(itemDao.findBySaleId(saleId), new Function<Item, ItemDto>() {
            @Override
            public ItemDto apply(Item s) {
                return new ItemDto(s.getSale().getId(), s.getProduct().getId(), s.getProduct().getName(), s.getProduct().getPrice(), s.getQuantity(), s.getDiscount());
            }
        });
    }

    @Override
    public void update(ItemDto dto) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ItemDto> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemDto getById(Long id) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<ItemDto> list(Pageable pageable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ItemShortDto> shortList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
