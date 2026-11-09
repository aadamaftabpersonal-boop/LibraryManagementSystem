package com.lms.service;

import com.lms.domain.exception.ItemNotFoundException;
import com.lms.domain.item.Book;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CatalogTest {

    private final Catalog catalog = new Catalog();

    @Test
    void returnsAnAddedItemById() {
        Book book = new Book("B1", "Algorithms", "Cormen");
        catalog.add(book);

        assertThat(catalog.get("B1")).isSameAs(book);
    }

    @Test
    void unknownIdThrowsWithTheBriefsMessage() {
        assertThatThrownBy(() -> catalog.get("B9"))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessage("item B9 not found");
    }

    @Test
    void allIsReadOnly() {
        catalog.add(new Book("B1", "Algorithms", "Cormen"));

        assertThatThrownBy(() -> catalog.all().clear())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}