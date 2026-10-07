package com.example;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository {

    private final List<String> originalMessages;
    private final List<String> availableMessages;

    public MessageRepository(List<String> validMessages) {
        originalMessages = new ArrayList<>(validMessages);
        availableMessages = new ArrayList<>(validMessages);
    }

    /*
        Sélectionne le prochain message avec la stratégie fournie.
        Recharge la liste lorsque tous les messages ont été utilisés,
        puis retire le message sélectionné.

        Paramètres :
            strategy – stratégie de sélection du message

        Retourne :
            message sélectionné
    */
    public String getNextMessage(MessageStrategy strategy) {
        if (availableMessages.isEmpty()) {
            availableMessages.addAll(originalMessages);
        }

        String selected = strategy.determineMessage(availableMessages);
        availableMessages.remove(selected);
        return selected;
    }
}