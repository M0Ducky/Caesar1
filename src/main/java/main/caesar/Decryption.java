package main.caesar;

import javafx.scene.control.TextArea;

import static main.caesar.CaesarApp.mainStage;

public class Decryption {
    public void setEncryptedText(String encryptedText) {
        this.encryptedText = encryptedText;
    }

    String encryptedText;
    String decryptedText;

    public Decryption (String encryptedText) {
        this.encryptedText = encryptedText;
    }

    int semanticPattern1;
    public int getSemanticPattern1() {
        return semanticPattern1;
    }

    int semanticPattern2;
    public int getSemanticPattern2() {
        return semanticPattern2;
    }

    public String decryptText(int offset) throws InterruptedException {
        String pattern1 = ", ";
        String pattern2 = ". ";
        //for (int i = 1; i<Alphabet.size; i++) {
        Encryption encryption = new Encryption(encryptedText);
        decryptedText = encryption.encryptText(offset);
        semanticPattern1 = countOccurencies(decryptedText, pattern1);
        semanticPattern2 = countOccurencies(decryptedText, pattern2);
        //}
        return decryptedText;
    }

    public void decryptTextStatistically() {

    }

    public int countOccurencies(String stringSource, String stringToBeFound) {
        int count = 0, fromIndex = 0;

        while ((fromIndex = stringSource.indexOf(stringToBeFound, fromIndex)) != -1 ){

            count++;
            fromIndex++;
        }

        return count;
    }
}
