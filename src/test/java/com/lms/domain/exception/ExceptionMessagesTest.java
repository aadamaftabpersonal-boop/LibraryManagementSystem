package com.lms.domain.exception;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExceptionMessagesTest {

    static Stream<Arguments> exceptions() {
        return Stream.of(
                arguments(new ItemNotFoundException("B9"), "item B9 not found"),
                arguments(new MemberNotFoundException("X1"), "member X1 not found"),
                arguments(ItemNotAvailableException.onLoan("B1"), "B1 is on loan"),
                arguments(ItemNotAvailableException.notBorrowable("R1"), "R1 cannot be borrowed"),
                arguments(new BorrowLimitExceededException("S1", 3), "S1 has reached the limit of 3"),
                arguments(new ItemNotOnLoanException("B4"), "B4 is not on loan"));
    }

    @ParameterizedTest(name = "{1}")
    @MethodSource("exceptions")
    void messageMatchesTheBriefExactly(LibraryException exception, String expectedMessage) {
        assertThat(exception).hasMessage(expectedMessage);
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }
}