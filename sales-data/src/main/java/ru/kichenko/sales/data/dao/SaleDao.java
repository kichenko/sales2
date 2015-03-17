/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.data.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kichenko.sales.model.Sale;
import ru.kichenko.sales.model.report.DiscountHourReport;

/**
 * Интерфейс dao для работы с продажами
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
public interface SaleDao extends JpaRepository<Sale, Long> {

    @Query("select s from Sale s join s.items i where i.product.id = :productId")
    public List<Sale> findByProductId(@Param("productId") Long productId);

    @Query("select "
      + "new ru.kichenko.sales.model.report.DiscountHourReport( "
      + "s.date, "
      + "coalesce(count(distinct s.id),0), "
      + "hour(s.date), "
      + "sum(coalesce(i.quantity, 0)*coalesce(i.product.price,0)), "
      + "avg(coalesce(i.quantity,0)*coalesce(i.product.price,0)), "
      + "sum(coalesce(i.quantity,0)*coalesce(i.discount,0)), "
      + "sum((coalesce(i.quantity,0)*coalesce(i.product.price,0))-coalesce(i.discount,0)), "
      + "avg((coalesce(i.quantity,0)*coalesce(i.product.price,0))-coalesce(i.discount,0)) "
      + ") "
      + "from Sale s "
      + "join s.items i "
      + "group by s.date, hour(s.date) "
      + "order by s.date "
    )
    List<DiscountHourReport> selectDiscountHourReport();

}
