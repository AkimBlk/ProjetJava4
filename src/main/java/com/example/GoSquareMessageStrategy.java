package com.example;

public class GoSquareMessageStrategy extends GoBestMessageStrategy {

    @Override
    protected int calculateScore(String message) {
        return SquareMaGo.computeChange(message);
    }
}