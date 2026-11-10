package com.lms.domain.member;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.lms.domain.exception.BorrowLimitExceededException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MemberTest {

    static Stream<Arguments> memberTypes() {
        return Stream.of(
                arguments(new Student("S1", "Ananya Rao"), 3, 14),
                arguments(new Staff("T1", "Ravi Kumar"), 4, 21),
                arguments(new Faculty("F1", "Meera Iyer"), 5, 30));
    }

    @ParameterizedTest(name = "{0} -> max {1}, period {2}")
    @MethodSource("memberTypes")
    void eachMemberTypeDecidesItsOwnLimits(Member member, int maxItems, int loanPeriod) {
        assertThat(member.getMaxItems()).isEqualTo(maxItems);
        assertThat(member.getLoanPeriodDays()).isEqualTo(loanPeriod);
    }
    @Test
    void studentBelowTheLimitCanBorrow() {
        assertThatCode(() -> new Student("S1", "Ananya Rao").ensureCanBorrow(2))
                .doesNotThrowAnyException();
    }

    @Test
    void studentAtTheLimitIsRefused() {
        assertThatThrownBy(() -> new Student("S1", "Ananya Rao").ensureCanBorrow(3))
                .isInstanceOf(BorrowLimitExceededException.class)
                .hasMessage("S1 has reached the limit of 3");
    }
}