/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.service;

import java.util.List;
import ru.kichenko.sales.web.dto.ItemDto;
import ru.kichenko.sales.web.dto.ItemShortDto;

/**
 * Интерфейс позиций заказа
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
public interface ItemService extends CrudProductSaleService<ItemDto, ItemShortDto> {
    List<ItemDto> findBySaleId(Long saleId);
}
