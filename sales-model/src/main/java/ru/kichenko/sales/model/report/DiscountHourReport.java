/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;
import ru.kichenko.sales.model.AbstractBaseEntity;

/**
 * Pojo для модели отчета по часам
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountHourReport extends AbstractBaseEntity<Long> {

    protected LocalDateTime saleDate;
    protected Long saleCount;
    protected Integer saleHour;

    protected Long saleSumFull;
    protected Double saleSumFullAvg;
    protected Long saleSumDiscount;
    protected Long saleSumFullWithDiscount;
    protected Double saleSumFullAvgWithDiscount;

}
