/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.data.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kichenko.sales.model.Item;

/**
 * Интерфейс dao для работы с позицией продажи
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
public interface ItemDao extends JpaRepository<Item, Item.Id> {

    @Query("select i from Item i where i.sale.id = :saleId")
    List<Item> findBySaleId(@Param("saleId") Long saleId);

    @Query("select 1 from Item i where i.sale.id = :saleId and i.product.id=:productId")
    Long findBySaleIdAndProductId(@Param("saleId") Long saleId, @Param("productId") Long productId);
}
