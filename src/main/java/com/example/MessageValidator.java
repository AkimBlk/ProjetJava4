package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
Vérifie la validité des messages et charge les messages valides
à partir d'un fichier.
Un message est considéré comme invalide s'il est vide, contient
une adresse avec "http://" ou ne contient aucune lettre.

- retourne la liste de message valide.
*/
public class MessageValidator {

    /**
     * Lit le fichier et retourne uniquement les messages valides.
     */
    public static List<String> loadValidMessages(String filePath) {
        List<String> validMessages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {
                if (isValid(line)) {
                    validMessages.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return validMessages;
    }

    /**
     * Vérifie si un message est valide.
     */
    public static boolean isValid(String message) {

        // Ligne vide
        if (message.isEmpty()) {
            return false;
        }

        if (message.contains("http://")) {
            return false;
        }

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }

        // Aucune lettre trouvée
        return false;
    }
}