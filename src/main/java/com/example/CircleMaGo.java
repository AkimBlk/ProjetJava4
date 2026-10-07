package com.example;

public class CircleMaGo extends MaGo {

    private static final int GAIN_PERCENTAGE = 5;
    private static final int LOSS_PERCENTAGE = 10;
    private static final int PERCENTAGE = 100;

    public CircleMaGo(MaGoObserver observer) {
        super(observer);
    }

    @Override
    protected int computeStatusChange(String text) {
        return computeChange(text);
    }

    /*
        Calcule la variation de statut d'un CircleMaGo en fonction du nombre
        de signes de ponctuation '!' et '?' présents dans le message.
        Chaque signe de ponctuation provoque un gain de ..% du statut maximal.
        Si aucun signe de ponctuation n'est présent, une perte de ..% du
        statut maximal est appliquée.

        Paramètres :
            message – message dont la ponctuation est analysée

        Retourne :
            variation de statut calculée à partir de la ponctuation du message
    */
    //utiliser en static pour permettre de voir le meilleurs score par message (GoBestMessageStrategie)
    public static int computeChange(String message) {
        int nbPonctuation = 0;

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if (c == '!' || c == '?') {
                nbPonctuation++;
            }
        }

        if (nbPonctuation > 0) {
            return nbPonctuation * (getMaxStatus() * GAIN_PERCENTAGE / PERCENTAGE);
        } else {
            return -(getMaxStatus() * LOSS_PERCENTAGE / PERCENTAGE);
        }
    }

    @Override
    public boolean isCircle() {
        return true;
    }
}