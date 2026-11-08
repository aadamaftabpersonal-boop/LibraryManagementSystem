package com.lms.domain.item;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LibraryItemTest {

    @Test
    void bookIsBorrowableAtTwoRupeesPerDay() {
        Book book = new Book("B1", "Introduction to Algorithms", "Cormen");

        assertThat(book).isInstanceOf(Borrowable.class);
        assertThat(book.getFinePerDay()).isEqualTo(2);
        assertThat(book.getType()).isEqualTo(ItemType.BOOK);
        assertThat(book.getAuthor()).isEqualTo("Cormen");
    }

    @Test
    void magazineIsBorrowableAtOneRupeePerDay() {
        Magazine magazine = new Magazine("M1", "Resonance", "Oct 2026");

        assertThat(magazine).isInstanceOf(Borrowable.class);
        assertThat(magazine.getFinePerDay()).isEqualTo(1);
        assertThat(magazine.getType()).isEqualTo(ItemType.MAGAZINE);
        assertThat(magazine.getIssue()).isEqualTo("Oct 2026");
    }

    @Test
    void referenceBookIsNotBorrowable() {
        LibraryItem reference = new ReferenceBook("R1", "Mahabharata", "Vyasa");

        assertThat(reference).isNotInstanceOf(Borrowable.class);
        assertThat(reference.getType()).isEqualTo(ItemType.REFERENCE);
    }

    @Test
    void keepsIdAndTitleWithSpaces() {
        LibraryItem book = new Book("B4", "Compilers: Principles, Techniques, and Tools", "Aho");

        assertThat(book.getId()).isEqualTo("B4");
        assertThat(book.getTitle()).isEqualTo("Compilers: Principles, Techniques, and Tools");
    }

    @Test
    void itemsWithSameIdAreEqual() {
        assertThat(new Book("B1", "A", "X")).isEqualTo(new Book("B1", "B", "Y"));
    }

    @Test
    void rejectsNullId() {
        assertThatThrownBy(() -> new Book(null, "Title", "Author"))
                .isInstanceOf(NullPointerException.class);
    }
}