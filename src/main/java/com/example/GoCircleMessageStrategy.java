package com.example;

public class GoCircleMessageStrategy extends GoBestMessageStrategy {

    @Override
    protected int calculateScore(String message) {
        return CircleMaGo.computeChange(message);
    }
}