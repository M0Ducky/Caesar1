package main.caesar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Encryption {

    public String encryptedText;

    public Encryption (String encryptedText) {
        this.encryptedText=encryptedText;
    }

    public String encryptText(Integer offset) {

        StringBuilder stringBuilder = new StringBuilder();
        char originalChar;
        char encryptedChar;
        int index;
        boolean upperCase = false;
        for (int i = 0; i< encryptedText.length(); i++ ) {
            originalChar = encryptedText.charAt(i);
            if (Character.isUpperCase(originalChar)) upperCase = true;
            originalChar = Character.toLowerCase(originalChar);
            if (Alphabet.charMap.containsKey(originalChar)) {
                index = Alphabet.charMap.get(originalChar);
                encryptedChar = Alphabet.indexMap.get(determineNewIndex(index, offset));
                if (upperCase) encryptedChar = Character.toUpperCase(encryptedChar);
                stringBuilder.append(encryptedChar);
            }
            upperCase = false;
        }
        encryptedText = stringBuilder.toString();
        return encryptedText;
    }

    public int determineNewIndex (int index, int offset) {
        int newUncheckedIndex = index + offset;
        int newCheckedIndex;
        if (newUncheckedIndex < 0) {
            newCheckedIndex = Alphabet.size + newUncheckedIndex;
        } else if (newUncheckedIndex < Alphabet.size) {
            newCheckedIndex = newUncheckedIndex;
        } else {
            newCheckedIndex = newUncheckedIndex - Alphabet.size;
        }
        return newCheckedIndex;
    }
}
