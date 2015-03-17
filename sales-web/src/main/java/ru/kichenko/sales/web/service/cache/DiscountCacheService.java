/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.service.cache;

import java.util.concurrent.ThreadLocalRandom;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kichenko.sales.data.dao.DiscountDao;
import ru.kichenko.sales.data.dao.ProductDao;
import ru.kichenko.sales.model.Discount;
import ru.kichenko.sales.model.Product;
import ru.kichenko.sales.web.dto.ProductDto;
import ru.kichenko.sales.web.dto.SpecialProductOfferDto;

/**
 * Сервис скидок на продукцию
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Service
public class DiscountCacheService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private DiscountDao discountDao;

    @Autowired
    @Value("5")
    private Integer percentMin;

    @Autowired
    @Value("10")
    private Integer percentMax;

    @Value("specialProductOffer")
    private String cacheKey;

    /**
     * Получить название кэш-ключа
     *
     * @return кэш-ключ
     */
    public String getCacheKey() {
        return cacheKey;
    }

    /**
     * Сгенерировать и сохранить в кэш информацию о продукте со скидкой.
     *
     * @return информация о продукте со скидкой
     */
    @Transactional
    @CachePut(value = "discountCache", key = "#root.target.cacheKey")
    public SpecialProductOfferDto generateDiscountProductInfo() {

        //случайный % скидки
        int percent = ThreadLocalRandom.current().nextInt(percentMin, percentMax);

        //случайный продукт в пределах min, max #id
        Product p = productDao.findOne(ThreadLocalRandom.current().nextLong(productDao.selectMinId(), productDao.selectMaxId()));

        //сохраняем инфу о скидке в БД
        discountDao.save(new Discount(LocalDateTime.now(), p, percent));

        //кладем инфу о скидке в кэш
        return new SpecialProductOfferDto(new ProductDto(p.getId(), p.getName(), p.getPrice()), percent);
    }

    /**
     * Запросить из кэша информацию о продукте со скидкой, если нет - вернуть
     * null.
     *
     * @return информация о продукте со скидкой или null
     */
    @Cacheable(value = "discountCache", key = "#root.target.cacheKey")
    public SpecialProductOfferDto getDiscountProductInfo() {
        return null;
    }
}
