package com.lms.domain.member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberFactoryTest {

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({"STUDENT, Student", "STAFF, Staff", "FACULTY, Faculty"})
    void createsTheRightMemberType(String type, String expectedClass) {
        Member member = MemberFactory.create(type, "M1", "Some Name");

        assertThat(member.getClass().getSimpleName()).isEqualTo(expectedClass);
    }

    @Test
    void keepsIdAndNameWithSpaces() {
        Member member = MemberFactory.create("STAFF", "T1", "Ravi Kumar");

        assertThat(member.getId()).isEqualTo("T1");
        assertThat(member.getName()).isEqualTo("Ravi Kumar");
    }

    @Test
    void rejectsAnUnknownType() {
        assertThatThrownBy(() -> MemberFactory.create("GUEST", "G1", "Guest"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("unknown member type: GUEST");
    }
}