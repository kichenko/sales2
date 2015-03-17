/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto продукта со скидкой
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialProductOfferDto {

    protected ProductDto product ;
    protected Integer percent;
}
