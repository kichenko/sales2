/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kichenko.sales.model.Product;

/**
 * Интерфейс dao для работы с продуктами
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
public interface ProductDao extends JpaRepository<Product, Long> {

    @Query("select max(p.id) from Product p")
    Long selectMaxId();

    @Query("select min(p.id) from Product p")
    Long selectMinId();
}
