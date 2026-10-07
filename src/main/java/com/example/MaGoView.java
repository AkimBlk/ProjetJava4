package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaGoView implements MaGoObserver {

    private Stage stage;
    private Scene scene;

    private final Map<MaGoSubject, Shape> maGoShapesMap = new HashMap<>();

    private final int vboxSpacing = 20;
    private final int vboxPadding = 20;
    private final int hboxSpacing = 20;
    private final int hboxPadding = 20;
    private final int winHeight = 600;
    private final int winWidth = 900;

    private final int colorSquareSize = 20;
    private final double radiusAverageStatus = 15;

    private Label averageStatusLabel = new Label("Average Status:");
    private Label negativeColorLabel = new Label("Negative Color");
    private Label positiveColorLabel = new Label("Positive Color");
    private Label cooldownLabel = new Label("...");
    private Label messageLabel = new Label(" ....");

    private Circle averageStatusIndicator = new Circle(radiusAverageStatus, Color.WHITE);
    private Rectangle negativeColorSquare = new Rectangle(colorSquareSize, colorSquareSize, Color.BLUE);
    private Rectangle positiveColorSquare = new Rectangle(colorSquareSize, colorSquareSize, Color.RED);

    private RadioButton randomRadio = new RadioButton("Random");
    private RadioButton goCircleRadio = new RadioButton("GoCircle");
    private RadioButton goSquareRadio = new RadioButton("GoSquare");
    private RadioButton goAnyRadio = new RadioButton("GoAny");
    private Button nextMessageButton = new Button("->");

    private HBox maGoZone = new HBox();

    public MaGoView(Stage stage) {
        this.stage = stage;

        HBox topZone = createTopZone();
        HBox middleZone = createMiddleZone();
        HBox bottomZone = createBottomZone();

        VBox root = new VBox(topZone, middleZone, bottomZone);
        root.setSpacing(vboxSpacing);
        root.setPadding(new Insets(vboxPadding));
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, winWidth, winHeight);
        stage.setScene(scene);
        stage.show();
    }

    private HBox createTopZone() {
        HBox averageGroup = new HBox(averageStatusLabel, averageStatusIndicator);
        averageGroup.setSpacing(10);
        averageGroup.setAlignment(Pos.CENTER);
        averageStatusIndicator.setStroke(Color.BLACK);

        HBox negativeColorGroup = new HBox(negativeColorSquare, negativeColorLabel);
        negativeColorGroup.setSpacing(10);
        negativeColorGroup.setAlignment(Pos.CENTER);

        HBox positiveColorGroup = new HBox(positiveColorSquare, positiveColorLabel);
        positiveColorGroup.setSpacing(10);
        positiveColorGroup.setAlignment(Pos.CENTER);

        HBox topZone = new HBox(averageGroup, negativeColorGroup, positiveColorGroup);
        topZone.setSpacing(hboxSpacing);
        topZone.setPadding(new Insets(hboxPadding));
        topZone.setAlignment(Pos.CENTER);
        topZone.setStyle("-fx-border-color: black;");

        return topZone;
    }

    private HBox createMiddleZone() {
        messageLabel.setStyle("-fx-font-size: 16px;");
        messageLabel.setWrapText(true);

        ScrollPane scroller = new ScrollPane(messageLabel);
        scroller.setPrefViewportWidth(500);
        scroller.setPrefViewportHeight(220);
        scroller.setFitToWidth(true);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox counterAndButton = new VBox(cooldownLabel, nextMessageButton);
        counterAndButton.setSpacing(vboxSpacing);
        counterAndButton.setAlignment(Pos.CENTER);

        ToggleGroup group = new ToggleGroup();
        randomRadio.setToggleGroup(group);
        goCircleRadio.setToggleGroup(group);
        goSquareRadio.setToggleGroup(group);
        goAnyRadio.setToggleGroup(group);
        randomRadio.setSelected(true);

        VBox scrollModeChoice = new VBox(randomRadio, goCircleRadio, goSquareRadio, goAnyRadio);
        scrollModeChoice.setSpacing(vboxSpacing);
        scrollModeChoice.setAlignment(Pos.CENTER);

        HBox middleZone = new HBox(scroller, counterAndButton, scrollModeChoice);
        middleZone.setSpacing(hboxSpacing);
        middleZone.setPadding(new Insets(hboxPadding));
        middleZone.setAlignment(Pos.CENTER);
        middleZone.setStyle("-fx-border-color: black;");

        return middleZone;
    }

    private HBox createBottomZone() {
        maGoZone.setSpacing(20);
        maGoZone.setAlignment(Pos.CENTER);
        maGoZone.setPadding(new Insets(hboxPadding));
        maGoZone.setStyle("-fx-border-color: black;");

        return maGoZone;
    }

    /*
        Crée les représentations graphiques des MaGos de la population.
        La taille des formes est calculée en fonction du nombre de MaGos afin
        de permettre leur affichage dans la zone prévue à cet effet.

        Paramètres :
            population – liste des MaGos à afficher
    */
    public void createMaGoShapes(List<MaGo> population) {
        double size = 200 / Math.sqrt(population.size());

        for (MaGo mago : population) {
            addMaGo(mago, size);
        }
    }


    /*
        Crée la forme graphique correspondant à un MaGo et l'ajoute à la zone
        d'affichage.
        Un CircleMaGo est représenté par un cercle tandis qu'un SquareMaGo est
        représenté par un carré. La couleur de la forme est déterminée à partir
        du statut actuel du MaGo.

        Paramètres :
            mago – MaGo dont la représentation doit être créée
            size – taille de la zone réservée à la représentation
    */
    private void addMaGo(MaGo mago, double size) {
        Shape shape;

        if (mago.isCircle()) {
            shape = new Circle(size * 0.2);
        } else {
            shape = new Rectangle(size * 0.4, size * 0.4);
        }

        shape.setStroke(Color.BLACK);
        shape.setFill(statusToColor(mago.getStatusValue()));

        StackPane container = new StackPane(shape);
        container.setPrefSize(size, size);
        container.setMinSize(size, size);
        container.setMaxSize(size, size);
        container.setStyle(
                "-fx-border-color: black;" +
                "-fx-border-radius: 20;" +
                "-fx-background-radius: 20;" +
                "-fx-background-color: white;"
        );

        maGoZone.getChildren().add(container);
        maGoShapesMap.put(mago, shape);
    }

    /*
        Associe une action au clic sur la représentation graphique d'un MaGo.

        Paramètres :
            mago – MaGo auquel l'action doit être associée
            handler – action exécutée lors du clic sur le MaGo
    */
    public void setOnMaGoClicked(MaGo mago, EventHandler<MouseEvent> handler) {
        Shape shape = maGoShapesMap.get(mago);
        shape.setOnMouseClicked(handler);
    }

    /*
        Convertit un statut en couleur selon son éloignement de zéro.
        Les statuts négatifs sont représentés par une couleur allant du bleu
        vers le blanc, tandis que les statuts positifs vont du blanc vers le rouge.
        Le statut est d'abord limité aux valeurs minimale et maximale autorisées.

        Paramètres :
            status – statut à convertir en couleur

        Retourne :
            couleur correspondant au statut fourni
    */
    private Color statusToColor(int status) {
        int min = MaGo.getMinStatus();
        int max = MaGo.getMaxStatus();

        status = Math.max(min, Math.min(max, status));

        if (status < 0) {
            double ratio = (double) (status - min) / (0 - min);
            return Color.color(
                ratio, 
                ratio,
                 1.0
            );
        }

        double ratio = (double) status / max;
        return Color.color(
            1.0, 
            1.0 - ratio, 
            1.0 - ratio
        );
    }

    /*
        Ouvre une fenêtre modale affichant les informations d'un MaGo.
        La fenêtre affiche son statut actuel et permet d'introduire une nouvelle
        valeur de statut. La nouvelle valeur n'est appliquée que si elle est valide.

        Paramètres :
            mago – MaGo dont les informations doivent être affichées
    */
    public void openMaGoWindow(MaGo mago) {
        Stage popup = new Stage();
        popup.initOwner(stage);
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Informations du MaGo");

        Label statusLabel = new Label("Actual Status");
        Label statusValueLabel = new Label(String.valueOf(mago.getStatusValue()));
        statusValueLabel.setMinWidth(50);
        statusValueLabel.setAlignment(Pos.CENTER);
        statusValueLabel.setStyle("-fx-border-color: black; -fx-padding: 5;");

        HBox statusRow = new HBox(20, statusLabel, statusValueLabel);
        statusRow.setAlignment(Pos.CENTER_LEFT);

        Label newValueLabel = new Label("New Value in [" + MaGo.getMinStatus() + " : " + MaGo.getMaxStatus() + "]");
        TextField newValueField = new TextField();
        newValueField.setMinWidth(50);

        HBox newValueRow = new HBox(20, newValueLabel, newValueField);
        newValueRow.setAlignment(Pos.CENTER_LEFT);

        Button confirmButton = new Button("Confirm New Value");

        confirmButton.setOnAction(e -> {
            String text = newValueField.getText();
            if (isValidStatusInput(text)) {
                mago.setStatus(Integer.parseInt(text));
                popup.close();
            }
        });

        VBox root = new VBox(20, statusRow, newValueRow, confirmButton);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        popup.setScene(new Scene(root, 400, 250));
        popup.showAndWait();
    }


    /*
        Vérifie si une chaîne représente un statut valide.
        La valeur doit être un entier compris entre le statut minimal et le statut
        maximal autorisés par la classe MaGo.

        Paramètres :
            value – valeur à vérifier

        Retourne :
            true si la valeur est un entier compris dans l'intervalle autorisé,
            false sinon
    */
    private boolean isValidStatusInput(String value) {
        try {
            int status = Integer.parseInt(value);

            return status >= MaGo.getMinStatus() && status <= MaGo.getMaxStatus();

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
        Met à jour l'affichage graphique d'un MaGo lorsque son statut est modifié.
        La couleur de sa représentation est recalculée à partir de son nouveau statut.

        Paramètres :
            subject – MaGo dont le statut a été modifié
    */
    @Override
    public void update(MaGoSubject subject) {
        Shape shape = maGoShapesMap.get(subject);
        shape.setFill(statusToColor(subject.getStatusValue()));
    }


    public void displayAverage(double average) {
        averageStatusIndicator.setFill(statusToColor((int) Math.round(average)));
    }
    public void displayCooldown(int seconds) {
        cooldownLabel.setText(String.valueOf(seconds));
    }
    public void displayMessage(String message) {
        messageLabel.setText(message);
    }


    //getter
    public RadioButton getRandomRadio() {
        return randomRadio;
    }
    public RadioButton getGoCircleRadio() {
        return goCircleRadio;
    }
    public RadioButton getGoSquareRadio() {
        return goSquareRadio;
    }
    public RadioButton getGoAnyRadio() {
        return goAnyRadio;
    }
    public Button getNextMessageButton() {
        return nextMessageButton;
    }
}