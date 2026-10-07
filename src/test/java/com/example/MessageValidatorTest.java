package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageValidatorTest {

    @Test
    void validMessageShouldReturnTrue() {
        assertTrue(MessageValidator.isValid("Il te faut atteindre 88 miles à l’heure !"));
    }

    @Test
    void messageContainingLettersAndNumbersShouldReturnTrue() {
        assertTrue(MessageValidator.isValid("42 est la réponse."));
    }

    @Test
    void onlyNumbersShouldReturnFalse() {
        assertFalse(MessageValidator.isValid("1234567890"));
    }

    @Test
    void httpMessageShouldReturnFalse() {
        assertFalse(MessageValidator.isValid("http://google.com"));
    }

    @Test
    void emptyMessageShouldReturnFalse() {
        assertFalse(MessageValidator.isValid(""));
    }

    @Test
    void onlySymbolsShouldReturnFalse() {
        assertFalse(MessageValidator.isValid("!!!???"));
    }
}