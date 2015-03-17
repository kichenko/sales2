/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kichenko.sales.data.dao.SaleDao;
import ru.kichenko.sales.model.report.DiscountHourReport;
import ru.kichenko.sales.web.dto.report.DiscountHourReportDto;

/**
 * Сервис отчетов
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Service
public class DiscountReportService {

    @Autowired
    private SaleDao saleDao;

    /**
     * Отчет - почасовая статистика продаж
     *
     * @return список статистики продаж
     */
    @Transactional(readOnly = true)
    public List<DiscountHourReportDto> selectDiscountHourReport() {
        return Lists.transform(saleDao.selectDiscountHourReport(), new Function<DiscountHourReport, DiscountHourReportDto>() {
            @Override
            public DiscountHourReportDto apply(DiscountHourReport d) {
                return new DiscountHourReportDto(
                  d.getSaleDate(),
                  d.getSaleCount(),
                  d.getSaleHour(),
                  d.getSaleSumFull(),
                  d.getSaleSumFullAvg(),
                  d.getSaleSumDiscount(),
                  d.getSaleSumFullWithDiscount(),
                  d.getSaleSumFullAvgWithDiscount()
                );
            }
        });
    }
}
