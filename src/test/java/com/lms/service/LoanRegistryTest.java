package com.lms.service;

import com.lms.domain.exception.ItemNotOnLoanException;
import com.lms.domain.item.Book;
import com.lms.domain.loan.Loan;
import com.lms.domain.member.Student;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoanRegistryTest {

    private final LoanRegistry registry = new LoanRegistry();
    private final Loan loan = new Loan(
            new Book("B1", "Introduction to Algorithms", "Cormen"),
            new Student("S1", "Ananya Rao"), 0);

    @Test
    void openedLoanIsFoundAndCounted() {
        registry.open(loan);

        assertThat(registry.isOnLoan("B1")).isTrue();
        assertThat(registry.find("B1")).contains(loan);
        assertThat(registry.countFor("S1")).isEqualTo(1);
        assertThat(registry.countFor("T1")).isZero();
    }

    @Test
    void closingReturnsTheLoanAndFreesTheItem() {
        registry.open(loan);

        assertThat(registry.close("B1")).isSameAs(loan);
        assertThat(registry.isOnLoan("B1")).isFalse();
    }

    @Test
    void closingAnItemNotOnLoanThrowsWithTheBriefsMessage() {
        assertThatThrownBy(() -> registry.close("B1"))
                .isInstanceOf(ItemNotOnLoanException.class)
                .hasMessage("B1 is not on loan");
    }

    @Test
    void openingTheSameItemTwiceIsABug() {
        registry.open(loan);

        assertThatThrownBy(() -> registry.open(loan))
                .isInstanceOf(IllegalStateException.class);
    }
}