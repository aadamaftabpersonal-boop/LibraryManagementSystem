package com.lms.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimulatedClockTest {

    private final SimulatedClock clock = new SimulatedClock();

    @Test
    void startsOnDayZero() {
        assertThat(clock.today()).isZero();
    }

    @Test
    void advancingAddsUpAndReturnsTheNewDay() {
        assertThat(clock.advance(10)).isEqualTo(10);
        assertThat(clock.advance(5)).isEqualTo(15);
        assertThat(clock.today()).isEqualTo(15);
    }

    @Test
    void advancingByZeroKeepsTheDay() {
        clock.advance(7);

        assertThat(clock.advance(0)).isEqualTo(7);
    }

    @Test
    void cannotGoBackInTime() {
        assertThatThrownBy(() -> clock.advance(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}