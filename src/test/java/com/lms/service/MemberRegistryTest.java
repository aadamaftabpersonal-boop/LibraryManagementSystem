package com.lms.service;

import com.lms.domain.exception.MemberNotFoundException;
import com.lms.domain.member.Student;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRegistryTest {

    private final MemberRegistry registry = new MemberRegistry();

    @Test
    void returnsAnAddedMemberById() {
        Student student = new Student("S1", "Ananya Rao");
        registry.add(student);

        assertThat(registry.get("S1")).isSameAs(student);
    }

    @Test
    void unknownIdThrowsWithTheBriefsMessage() {
        assertThatThrownBy(() -> registry.get("X1"))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("member X1 not found");
    }
}