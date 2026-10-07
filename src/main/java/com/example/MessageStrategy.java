package com.example;

import java.util.List;

public interface MessageStrategy {

    String determineMessage(List<String> availableMessages);
}