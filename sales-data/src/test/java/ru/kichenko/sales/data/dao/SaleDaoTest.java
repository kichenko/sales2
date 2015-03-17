/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.data.dao;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kichenko.sales.data.config.PersistenceConfig;
import ru.kichenko.sales.model.Discount;
import ru.kichenko.sales.model.Item;
import ru.kichenko.sales.model.Product;
import ru.kichenko.sales.model.Sale;
import ru.kichenko.sales.model.report.DiscountHourReport;

/**
 * Класс для тестирования Product DAO
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@ContextConfiguration(classes = PersistenceConfig.class)
public class SaleDaoTest extends AbstractDaoTest<SaleDao> {

    @BeforeMethod
    public void setUp() {

        Product p1 = new Product("product-1", 75L, new ArrayList<Item>(0), new ArrayList<Discount>(0));
        Product p2 = new Product("product-1", 75L, new ArrayList<Item>(0), new ArrayList<Discount>(0));

        Sale s1 = new Sale(new LocalDateTime(2015, 3, 15, 12, 45), new ArrayList<Item>());
        Sale s2 = new Sale(new LocalDateTime(2015, 3, 15, 17, 45), new ArrayList<Item>());

        //product
        em.persist(p1);
        em.persist(p2);

        //sale
        em.persist(s1);
        em.persist(s2);

        Item item1 = new Item(s1, p1, 10, 0L);
        Item item2 = new Item(s1, p2, 100, 0L);

        //item sale#1
        em.persist(item1);
        em.persist(item2);

        Item item3 = new Item(s2, p1, 50, 25L);
        Item item4 = new Item(s2, p2, 500, 0L);

        //item sale#2
        em.persist(item3);
        em.persist(item4);
    }

    @Test
    @Transactional
    public void testReport() {
        List<DiscountHourReport> list = dao.selectDiscountHourReport();
        assertNotNull(list);
        assertEquals(list.size(), 2);

        assertEquals(list.get(0).getSaleHour(), new Integer(12));
        assertEquals(list.get(1).getSaleHour(), new Integer(17));

        assertEquals(list.get(0).getSaleSumFull(), new Long(8250));
        assertEquals(list.get(1).getSaleSumFull(), new Long(41250));
    }
}
