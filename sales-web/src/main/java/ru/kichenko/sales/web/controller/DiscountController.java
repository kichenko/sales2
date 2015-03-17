/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kichenko.sales.web.core.ExtListPage;
import ru.kichenko.sales.web.dto.DiscountDto;
import ru.kichenko.sales.web.service.DiscountService;

/**
 * Контроллер скидочных предложений
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Slf4j
@Controller
@RequestMapping(value = "/discounts")
public class DiscountController extends AbstractController {

    @Autowired
    private DiscountService service;

    @RequestMapping(method = RequestMethod.GET)
    public String discount(Model model) {
        return "discount";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ExtListPage<DiscountDto> list(Pageable pageable) throws Exception {
        return new ExtListPage<>(service.list(pageable));
    }
}
