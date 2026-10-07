package com.example;

public class SquareMaGo extends MaGo {

    private static final double RATIO_VOWEL_CONSONANT = 0.89;
    private static final double GAIN = 0.10;

    public SquareMaGo(MaGoObserver observer) {
        super(observer);
    }

    @Override
    protected int computeStatusChange(String text) {
        return computeChange(text);
    }

    /*
        Calcule la variation de statut selon le rapport entre le nombre de voyelles
        et de consonnes du message.

        Paramètres :
            text – message à analyser

        Retourne :
            gain de ..% du statut maximal si le rapport dépasse le seuil,
            perte de ..% sinon
    */
    public static int computeChange(String text) {
        double ratio = vowelToConsonantRatio(text);

        int gain = (int) (MaGo.getMaxStatus() * GAIN);

        if (ratio > RATIO_VOWEL_CONSONANT) {
            return gain;
        } else {
            return -gain;
        }
    }


    /*
        Calcule le rapport entre le nombre de voyelles et de consonnes du message.

        Paramètres :
            text – message à analyser

        Retourne :
            rapport voyelles/consonnes
    */
    private static double vowelToConsonantRatio(String text) {
        int vowel = 0;
        int consonant = 0;

        text = text.toLowerCase();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y') {
                vowel++;
            } else if (c >= 'a' && c <= 'z') {
                consonant++;
            }
        }

        if (consonant == 0) {
            return 0;
        }

        return (double) vowel / consonant;
    }

    @Override
    public boolean isCircle() {
        return false;
    }
}