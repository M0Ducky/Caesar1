package main.caesar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Alphabet {
    public static ArrayList<Character> cipher;
    public static Map<Integer, Character> indexMap;
    public static Map<Character, Integer> charMap;
    public static int size;

    public static void initialize () {
        cipher = new ArrayList<>(Arrays.asList('а','б','в','г','д',
                'е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ь',
                'ы','ъ','э','ю','я','.',',','"',':','-','?','!',' '));
        size = cipher.size();
        indexMap = new HashMap<>();
        for (int i = 0; i < cipher.size(); i++) {
            indexMap.put(i, cipher.get(i));
        }
        charMap = new HashMap<>();
        for (int j = 0; j < cipher.size(); j++) {
            charMap.put(cipher.get(j), j);
        }
    }



}
