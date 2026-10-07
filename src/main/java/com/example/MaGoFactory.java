package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class MaGoFactory {

    private static final int MIN_MAGO = 2;
    private static final int MAX_MAGO = 8;

    private MaGoFactory() {
    }

    /*
    Crée un nombre aléatoire de MaGos compris entre 2 et 8.
    Détermine le type de chaque MaGo à partir des chiffres du timestamp Unix
    au moment de leur création, puis initialise chaque MaGo.

    Paramètres :
        observer – observateur associé aux MaGos créés

    Retourne :
        liste des MaGos créés
    */
    public static List<MaGo> createMaGos(MaGoObserver observer) {
        Random random = new Random();

        int nbMaGo = random.nextInt(MAX_MAGO - MIN_MAGO + 1) + MIN_MAGO;

        List<MaGo> maGos = new ArrayList<>();

        long unixTime = System.currentTimeMillis() / 1000L;
        //System.out.println(unixTime);

        long divisor = 1;

        for (int i = 0; i < nbMaGo; i++) {
            int digit = (int) ((unixTime / divisor) % 10);//modulo 10
            divisor *= 10;

            if (digit % 2 == 0) {
                maGos.add(new CircleMaGo(observer));
            } else {
                maGos.add(new SquareMaGo(observer));
            }
        }

        return maGos;
    }
}