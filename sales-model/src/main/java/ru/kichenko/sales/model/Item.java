/*
 * (c) Сергей Киченко, 2015. Все права защищены.
 */
package ru.kichenko.sales.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Позиция продажи
 *
 * @author Сергей Киченко
 * @created 21.02.15 00:00
 */
@Getter
@Setter
public class Item extends AbstractBaseEntity<Item.Id> {

    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {

        private Long saleId;
        private Long productId;
    }

    private Sale sale;
    private Product product;
    private Integer quantity;
    private Long discount;

    public Item() {
        id = new Id();
    }

    public Item(Sale sale, Product product, Integer quantity, Long discount) {
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
        this.id = new Id(sale.getId(), product.getId());
    }
}
