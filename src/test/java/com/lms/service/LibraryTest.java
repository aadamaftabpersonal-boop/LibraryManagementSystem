package com.lms.service;

import com.lms.domain.item.Book;
import com.lms.domain.item.ItemStatus;
import com.lms.domain.item.LibraryItem;
import com.lms.domain.item.Magazine;
import com.lms.domain.item.ReferenceBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();
    }

    @Test
    void emptyLibraryHasEmptyInventory() {
        assertThat(library.inventory()).isEmpty();
    }

    @Test
    void inventoryIsSortedById() {
        library.addItem(new Book("B2", "Modern Operating Systems", "Tanenbaum"));
        library.addItem(new Magazine("M1", "Resonance", "Oct 2026"));
        library.addItem(new Book("B1", "Introduction to Algorithms", "Cormen"));

        assertThat(library.inventory())
                .extracting(LibraryItem::getId)
                .containsExactly("B1", "B2", "M1");
    }

    @Test
    void newBookIsAvailable() {
        Book book = new Book("B1", "Introduction to Algorithms", "Cormen");
        library.addItem(book);

        assertThat(library.statusOf(book)).isEqualTo(ItemStatus.AVAILABLE);
    }

    @Test
    void referenceBookIsNotBorrowable() {
        ReferenceBook reference = new ReferenceBook("R1", "Mahabharata", "Vyasa");
        library.addItem(reference);

        assertThat(library.statusOf(reference)).isEqualTo(ItemStatus.NOT_BORROWABLE);
    }
}