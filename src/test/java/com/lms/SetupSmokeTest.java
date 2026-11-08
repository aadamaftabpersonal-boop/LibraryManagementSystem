package com.lms;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SetupSmokeTest {

    @Test
    void junitAndAssertjAreWorking() {
        assertThat(1 + 1).isEqualTo(2);
    }
}