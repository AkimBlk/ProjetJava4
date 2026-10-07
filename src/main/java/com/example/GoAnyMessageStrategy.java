package com.example;

import java.util.List;
import java.util.Random;

public class GoAnyMessageStrategy implements MessageStrategy {

    private final MessageStrategy circleStrategy;
    private final MessageStrategy squareStrategy;
    private final Random random = new Random();

    public GoAnyMessageStrategy(MessageStrategy circleStrategy, MessageStrategy squareStrategy) {
        this.circleStrategy = circleStrategy;
        this.squareStrategy = squareStrategy;
    }

    /*
        Choisit aléatoirement entre la stratégie destinée aux CircleMaGos
        et celle destinée aux SquareMaGos, puis utilise la stratégie choisie
        pour déterminer le prochain message.

        Paramètres :
            availableMessages – liste des messages encore disponibles

        Retourne :
            message déterminé par la stratégie choisie aléatoirement
    */
    @Override
    public String determineMessage(List<String> availableMessages) {
        int choice = random.nextInt(2) + 1;

        if (choice == 1) {
            //System.out.println("any = circle");
            return circleStrategy.determineMessage(availableMessages);
        }
        //System.out.println("any = square");
        return squareStrategy.determineMessage(availableMessages);
    }
}