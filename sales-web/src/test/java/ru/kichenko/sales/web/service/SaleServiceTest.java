/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.web.service;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.kichenko.sales.data.dao.SaleDao;
import ru.kichenko.sales.model.Item;
import ru.kichenko.sales.model.Sale;
import ru.kichenko.sales.web.dto.SaleDto;

/**
 * Тесты для сервиса продаж
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
public class SaleServiceTest {

    @InjectMocks
    private final SaleService service = new SaleServiceImpl();

    @Mock
    private SaleDao saleDao;

    private final Sale sale = new Sale(new LocalDateTime(),  new ArrayList<Item>(0));

    @BeforeMethod
    public void injectMocks() {

        MockitoAnnotations.initMocks(this);
        when(saleDao.findByProductId(anyLong())).thenReturn(Lists.newArrayList(sale));
    }

    @Test
    public void testFindByProductId() {
        Long productId = 0L;
        List<SaleDto> sales = service.findByProductId(productId);

        assertNotNull(sales);
        assertEquals(sales.size(), 1);

        assertNotNull(sales.get(0));
        assertEquals(sales.get(0).getId(), sale.getId());
        assertEquals(sales.get(0).getDate(), sale.getDate());

        verify(saleDao).findByProductId(productId);
    }
}
