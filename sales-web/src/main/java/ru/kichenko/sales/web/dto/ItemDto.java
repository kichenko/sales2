/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto позиции продажи
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto extends AbstractDto {

    private Long saleId;
    private Long productId;
    private String name;
    private Long price;
    private Integer quantity;
    private Long discount;
}
