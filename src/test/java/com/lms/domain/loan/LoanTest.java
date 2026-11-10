package com.lms.domain.loan;

import com.lms.domain.item.Book;
import com.lms.domain.member.Faculty;
import com.lms.domain.member.Member;
import com.lms.domain.member.Staff;
import com.lms.domain.item.Magazine;
import com.lms.domain.member.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LoanTest {

    private final Book book = new Book("B1", "Introduction to Algorithms", "Cormen");

    static Stream<Arguments> memberTypes() {
        return Stream.of(
                arguments(new Student("S1", "Ananya Rao"), 14),
                arguments(new Staff("T1", "Ravi Kumar"), 21),
                arguments(new Faculty("F1", "Meera Iyer"), 30));
    }

    @ParameterizedTest(name = "{0} borrowing on day 0 -> due day {1}")
    @MethodSource("memberTypes")
    void dueDayComesFromTheMembersLoanPeriod(Member member, int expectedDueDay) {
        assertThat(new Loan(book, member, 0).getDueDay()).isEqualTo(expectedDueDay);
    }

    @Test
    void dueDayCountsFromTheBorrowDay() {
        Loan loan = new Loan(book, new Staff("T1", "Ravi Kumar"), 10);

        assertThat(loan.getDueDay()).isEqualTo(31);
    }

    @Test
    void keepsItemMemberAndBorrowDay() {
        Student student = new Student("S1", "Ananya Rao");
        Loan loan = new Loan(book, student, 5);

        assertThat(loan.getItem()).isSameAs(book);
        assertThat(loan.getMember()).isSameAs(student);
        assertThat(loan.getBorrowDay()).isEqualTo(5);
    }
        @Test
    void returningOnTheDueDayHasNoFine() {
        Loan loan = new Loan(book, new Student("S1", "Ananya Rao"), 0);

        assertThat(loan.daysLate(14)).isZero();
        assertThat(loan.fineAsOf(14)).isZero();
    }

    @Test
    void lateBookCostsTwoRupeesPerDay() {
        Loan loan = new Loan(book, new Student("S1", "Ananya Rao"), 0);

        assertThat(loan.daysLate(20)).isEqualTo(6);
        assertThat(loan.fineAsOf(20)).isEqualTo(12);
    }

    @Test
    void lateMagazineCostsOneRupeePerDay() {
        Magazine magazine = new Magazine("M1", "Resonance", "Oct 2026");
        Loan loan = new Loan(magazine, new Student("S1", "Ananya Rao"), 0);

        assertThat(loan.fineAsOf(20)).isEqualTo(6);
    }

    @Test
    void overdueOnlyAfterTheDueDay() {
        Loan loan = new Loan(book, new Student("S1", "Ananya Rao"), 0);

        assertThat(loan.isOverdue(14)).isFalse();
        assertThat(loan.isOverdue(15)).isTrue();
    }
}