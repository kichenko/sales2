/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.data.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.test.context.ContextConfiguration;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kichenko.sales.data.config.PersistenceConfig;
import ru.kichenko.sales.model.Discount;
import ru.kichenko.sales.model.Item;
import ru.kichenko.sales.model.Product;

/**
 * Класс для тестирования Product DAO
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@ContextConfiguration(classes = PersistenceConfig.class)
public class ProductDaoTest extends AbstractDaoTest<ProductDao> {

    @BeforeMethod
    public void setUp() {
        em.persist(new Product("product-1", 75L, new ArrayList<Item>(0), new ArrayList<Discount>(0)));
    }

    @Test
    public void testFindAll() {
        List<Product> list = dao.findAll();

        assertNotNull(list);
        assertEquals(list.size(), 1);

        assertNotNull(list.get(0));
        assertEquals(list.get(0).getName(), "product-1");
        assertEquals(list.get(0).getPrice(), new Long(75L));
    }
}
