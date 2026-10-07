package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareMaGoTest {

    @Test
    void messageWithHighVowelRatioShouldGiveTen() {
        assertEquals(10, SquareMaGo.computeChange("aaaaab"));
    }

    @Test
    void messageWithLowVowelRatioShouldGiveMinusTen() {
        assertEquals(-10, SquareMaGo.computeChange("abbbbb"));
    }

    @Test
    void messageWithNoConsonantShouldGiveZero() {
        assertEquals(-10, SquareMaGo.computeChange("aeiouy"));
    }

    @Test
    void numbersAndPunctuationShouldBeIgnored() {
        assertEquals(10, SquareMaGo.computeChange("aaaaab123!!!"));
    }

    @Test
    void isCircleShouldReturnFalse() {
        SquareMaGo maGo = new SquareMaGo(null);

        assertFalse(maGo.isCircle());
    }
}