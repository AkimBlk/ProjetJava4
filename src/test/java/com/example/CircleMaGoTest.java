package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleMaGoTest {

    @Test
    void messageWithOneExclamationShouldGiveFive() {
        assertEquals(5, CircleMaGo.computeChange("Bonjour !"));
    }

    @Test
    void messageWithOneQuestionShouldGiveFive() {
        assertEquals(5, CircleMaGo.computeChange("Comment vas-tu ?"));
    }

    @Test
    void messageWithSeveralPunctuationsShouldGiveFivePerPunctuation() {
        assertEquals(15, CircleMaGo.computeChange("Bonjour ! Ça va ? Super !"));
    }

    @Test
    void messageWithoutPunctuationShouldGiveMinusTen() {
        assertEquals(-10, CircleMaGo.computeChange("Bonjour"));
    }

    @Test
    void messageWithNoQuestionOrExclamationShouldGiveMinusTen() {
        assertEquals(-10, CircleMaGo.computeChange("Bonjour, comment vas-tu."));
    }

    @Test
    void isCircleShouldReturnTrue() {
        CircleMaGo maGo = new CircleMaGo(null);

        assertTrue(maGo.isCircle());
    }
}