package com.example;

import java.util.List;
import java.util.Random;

public class RandomMessageStrategy implements MessageStrategy {

    private final Random random = new Random();

    @Override
    public String determineMessage(List<String> availableMessages) {
        int index = random.nextInt(availableMessages.size());
        return availableMessages.get(index);
    }
}