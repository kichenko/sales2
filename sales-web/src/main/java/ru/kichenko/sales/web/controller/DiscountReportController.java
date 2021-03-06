/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kichenko.sales.web.core.ExtList;
import ru.kichenko.sales.web.dto.report.DiscountHourReportDto;
import ru.kichenko.sales.web.service.DiscountReportService;

/**
 * Контроллер скидочных предложений
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Slf4j
@Controller
@RequestMapping(value = "/discounts/reports")
public class DiscountReportController extends AbstractController {

    @Autowired
    private DiscountReportService service;

    @RequestMapping(value = "/discount-by-hour", method = RequestMethod.GET)
    public String discountByHour(Model model) {
        return "report/discount-by-hour";
    }

    @ResponseBody
    @RequestMapping(value = "/list/discount-by-hour", method = RequestMethod.GET)
    public ExtList<DiscountHourReportDto> listDiscountByHour() throws Exception {
        return new ExtList<>(service.selectDiscountHourReport());
    }
}
