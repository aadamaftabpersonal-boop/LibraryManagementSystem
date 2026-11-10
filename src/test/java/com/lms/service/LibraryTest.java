package com.lms.service;

import com.lms.domain.item.Book;
import com.lms.domain.item.ItemStatus;
import com.lms.domain.item.LibraryItem;
import com.lms.domain.item.Magazine;
import com.lms.domain.item.ReferenceBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.lms.domain.exception.ItemNotAvailableException;
import com.lms.domain.exception.ItemNotFoundException;
import com.lms.domain.exception.ItemNotOnLoanException;
import com.lms.domain.exception.BorrowLimitExceededException;
import com.lms.domain.exception.MemberNotFoundException;
import com.lms.domain.member.Student;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        @Nested
    class BorrowingAndReturning {

        private final Book book = new Book("B1", "Introduction to Algorithms", "Cormen");
        private final ReferenceBook reference = new ReferenceBook("R1", "Mahabharata", "Vyasa");

        @BeforeEach
        void addItemsAndMember() {
            library.addItem(book);
            library.addItem(reference);
            library.addMember(new Student("S1", "Ananya Rao"));
        }

        @Test
        void borrowingGivesADueDayFromTheLoanPeriod() {
            assertThat(library.borrow("S1", "B1").getDueDay()).isEqualTo(14);
        }

        @Test
        void borrowedItemShowsAsOnLoan() {
            library.borrow("S1", "B1");

            assertThat(library.statusOf(book)).isEqualTo(ItemStatus.ON_LOAN);
            assertThat(library.findLoan("B1")).isPresent();
        }

        @Test
        void cannotBorrowAnItemAlreadyOnLoan() {
            library.borrow("S1", "B1");

            assertThatThrownBy(() -> library.borrow("S1", "B1"))
                    .isInstanceOf(ItemNotAvailableException.class)
                    .hasMessage("B1 is on loan");
        }

        @Test
        void cannotBorrowAReferenceBook() {
            assertThatThrownBy(() -> library.borrow("S1", "R1"))
                    .isInstanceOf(ItemNotAvailableException.class)
                    .hasMessage("R1 cannot be borrowed");
        }

        @Test
        void unknownMemberIsCheckedFirst() {
            assertThatThrownBy(() -> library.borrow("X1", "B9"))
                    .isInstanceOf(MemberNotFoundException.class)
                    .hasMessage("member X1 not found");
        }

        @Test
        void unknownItemIsReported() {
            assertThatThrownBy(() -> library.borrow("S1", "B9"))
                    .isInstanceOf(ItemNotFoundException.class)
                    .hasMessage("item B9 not found");
        }

        @Test
        void memberAtTheirLimitIsRefused() {
                library.addItem(new Book("B2", "Modern Operating Systems", "Tanenbaum"));
                library.addItem(new Book("B3", "The Art of Computer Programming", "Knuth"));
                library.addItem(new Book("B4", "Compilers", "Aho"));
                library.borrow("S1", "B1");
                library.borrow("S1", "B2");
                library.borrow("S1", "B3");

                assertThatThrownBy(() -> library.borrow("S1", "B4"))
                        .isInstanceOf(BorrowLimitExceededException.class)
                        .hasMessage("S1 has reached the limit of 3");
            }

        @Test
        void returningOnTimeHasNoFineAndFreesTheItem() {
                library.borrow("S1", "B1");

                assertThat(library.returnItem("B1")).isZero();
                assertThat(library.statusOf(book)).isEqualTo(ItemStatus.AVAILABLE);
            }

        @Test
        void returningAnItemNotOnLoanFails() {
                assertThatThrownBy(() -> library.returnItem("B1"))
                        .isInstanceOf(ItemNotOnLoanException.class)
                        .hasMessage("B1 is not on loan");
            }

        @Test
        void returningAnUnknownItemSaysNotFound() {
                assertThatThrownBy(() -> library.returnItem("B9"))
                        .isInstanceOf(ItemNotFoundException.class)
                        .hasMessage("item B9 not found");
            }
        @Test
        void lateReturnIsFinedByDaysLate() {
            library.borrow("S1", "B1");
            library.advance(20);
            assertThat(library.returnItem("B1")).isEqualTo(12);
        }
    }
}