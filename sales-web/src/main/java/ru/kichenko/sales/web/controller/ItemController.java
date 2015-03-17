/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kichenko.sales.web.core.Ext;
import ru.kichenko.sales.web.core.ExtList;
import ru.kichenko.sales.web.dto.ItemDto;
import ru.kichenko.sales.web.service.ItemService;

/**
 * Контроллер позиций заказа
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Slf4j
@Controller
@RequestMapping(value = "/items")
public class ItemController extends AbstractController {

    @Autowired
    private ItemService service;

    @RequestMapping(method = RequestMethod.GET)
    public String item(Model model) {
        return "item";
    }

    @ResponseBody
    @RequestMapping(value = "/list/{saleId}", method = RequestMethod.GET)
    public ExtList<ItemDto> findByProductId(@PathVariable("saleId") Long saleId) throws Exception {
        return new ExtList<>(service.findBySaleId(saleId));
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Ext<ItemDto> insert(@RequestBody ItemDto dto) throws Exception {
        return new Ext<>(service.insert(dto));
    }
}
