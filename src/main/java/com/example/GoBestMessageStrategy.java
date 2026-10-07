package com.example;

import java.util.List;

public abstract class GoBestMessageStrategy implements MessageStrategy {

    /*
        Parcourt les messages disponibles afin de sélectionner celui qui
        obtient le meilleur score.
        Le calcul du score est délégué aux classes filles grâce à la méthode
        calculateScore(), ce qui permet de partager la logique de sélection.

        Paramètres :
            availableMessages – liste des messages encore disponibles

        Retourne :
            message ayant obtenu le meilleur score
    */
    @Override
    public String determineMessage(List<String> availableMessages) {
        String best = availableMessages.get(0);
        int bestScore = calculateScore(best);

        for (String message : availableMessages) {
            int score = calculateScore(message);
            if (score > bestScore) {
                bestScore = score;
                best = message;
            }
        }
        return best;
    }

    protected abstract int calculateScore(String message);
}