/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.service.scheduler;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.kichenko.sales.web.service.cache.DiscountCacheService;

/**
 * Планировщик для генерации информации о продукте со скидкой по расписанию
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Slf4j
@Component
public class DiscountScheduler {

    @Autowired
    private DiscountCacheService discountCacheService;

    /**
     * Генерирует информацию о продукте со скидкой по расписанию. В первый раз
     * метод выполянется после создания бина, чтобы при старте приложения
     * скидочный продукт был инициализирован.
     *
     */
    @PostConstruct
    @Scheduled(cron = "0 * * * * *")
    //@Scheduled(cron = "0 0 0/1 * * ?")
    public void schedulerDiscountProductInfo() {
        discountCacheService.generateDiscountProductInfo();
    }
}
