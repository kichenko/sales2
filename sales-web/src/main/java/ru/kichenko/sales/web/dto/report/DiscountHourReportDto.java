/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;
import ru.kichenko.sales.web.dto.AbstractDto;

/**
 * Dto отчета почасовой статистики
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountHourReportDto extends AbstractDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected LocalDateTime saleDate;

    protected Long saleCount;
    protected Integer saleHour;

    protected Long saleSumFull;
    protected Double saleSumFullAvg;
    protected Long saleSumDiscount;
    protected Long saleSumFullWithDiscount;
    protected Double saleSumFullAvgWithDiscount;
}
