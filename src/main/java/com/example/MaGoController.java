package com.example;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class MaGoController {

    private final String fileName = "mess.msg";
    private final int messageInterval = 5;
    private final int speedOfTimeline=1000;

    private Timeline timeline;
    private int remainingSeconds;

    private MaGoView view;

    private List<MaGo> populationList;
    private MessageRepository messageRepository;

    private RandomMessageStrategy randomStrategy;
    private GoCircleMessageStrategy goCircleStrategy;
    private GoSquareMessageStrategy goSquareStrategy;
    private GoAnyMessageStrategy goAnyStrategy;
    private MessageStrategy actualStrategy;

    public MaGoController(Stage primaryStage) {
        view = new MaGoView(primaryStage);

        randomStrategy = new RandomMessageStrategy();
        goCircleStrategy = new GoCircleMessageStrategy();
        goSquareStrategy = new GoSquareMessageStrategy();
        goAnyStrategy = new GoAnyMessageStrategy(goCircleStrategy, goSquareStrategy);

        actualStrategy = randomStrategy;

        populationList = MaGoFactory.createMaGos(view);
        messageRepository = new MessageRepository(MessageValidator.loadValidMessages(fileName));

        view.createMaGoShapes(populationList);

        for (MaGo mago : populationList) {
            view.setOnMaGoClicked(mago, e -> openMaGoWindow(mago));
        }

        setActions();
        startCooldown();
    }

    private void resetCooldown() {
        remainingSeconds = messageInterval;
        view.displayCooldown(remainingSeconds);
    }

    private void startCooldown() {
        timeline = new Timeline(new KeyFrame(Duration.millis(speedOfTimeline), e -> {
            remainingSeconds--;

            view.displayCooldown(remainingSeconds);

            if (remainingSeconds <= 0) {
                displayNextMessage();
                resetCooldown();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void displayNextMessage() {
        String nextMessage = messageRepository.getNextMessage(actualStrategy);

        for (MaGo mago : populationList) {
            mago.reactionToMessage(nextMessage);
        }

        view.displayAverage(calculateAverageStatus());
        view.displayMessage(nextMessage);
    }

    private double calculateAverageStatus() {
        int total = 0;
        for (MaGo mago : populationList) {
            total += mago.getStatusValue();
        }
        return (double) total / populationList.size();
    }

    private void setActions() {
        view.getNextMessageButton().setOnAction(e -> {
            displayNextMessage();
            resetCooldown();
        });

        view.getRandomRadio().setOnAction(e -> actualStrategy = randomStrategy);
        view.getGoCircleRadio().setOnAction(e -> actualStrategy = goCircleStrategy);
        view.getGoSquareRadio().setOnAction(e -> actualStrategy = goSquareStrategy);
        view.getGoAnyRadio().setOnAction(e -> actualStrategy = goAnyStrategy);
    }

    private void openMaGoWindow(MaGo mago) {
        timeline.pause();
        view.openMaGoWindow(mago);
        timeline.play();
        view.displayAverage(calculateAverageStatus());
    }
}