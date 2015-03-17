/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;

/**
 * Dto скидки
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto extends AbstractDto {

    protected ProductDto product;
    protected Integer percent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected LocalDateTime date;
    protected Integer hour;

    public DiscountDto(Long id, ProductDto product, Integer percent, LocalDateTime date) {
        super(id);
        this.product = product;
        this.percent = percent;
        this.date = date;
        this.hour = date.getHourOfDay();
    }
}
