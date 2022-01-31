import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class AnalyzeContent {

    //Analyze 2 Content By amount of letters in text
    //TreeMap in use
    public String DecryptTextByAnalyze(String contentToBeDecrypted, String contentToAnalyze) {
        var mapOfEncryptedText = new TreeMap<Character, Double>();
        var mapTextToAnalyze = new TreeMap<Character, Double>();
        var temporary = new TreeMap<Double, Character>();
        var temporary2 = new TreeMap<Double, Character>();
        StringBuilder result = new StringBuilder();


        contentToBeDecrypted = contentToBeDecrypted.toLowerCase();
        contentToAnalyze = contentToAnalyze.toLowerCase();



        for (int i = 0; i < contentToBeDecrypted.length(); i++) {
            Character tempLetter = contentToBeDecrypted.charAt(i);
            if(mapOfEncryptedText.containsKey(tempLetter)){
                double k = mapOfEncryptedText.get(tempLetter);
                mapOfEncryptedText.put(tempLetter,k + 1);
            }
            else{
                mapOfEncryptedText.put(tempLetter, 1.0);
            }
        }
        mapOfEncryptedText = LetterContainsPercentage(mapOfEncryptedText, contentToBeDecrypted.length());
        temporary = ReplaceKeyAndValue(mapOfEncryptedText);
        var newMap = new TreeMap(Collections.reverseOrder());
        newMap.putAll(temporary);



        for (int i = 0; i < contentToAnalyze.length(); i++) {
            Character tempLetter = contentToAnalyze.charAt(i);
            if(mapTextToAnalyze.containsKey(tempLetter)){
                double k = mapTextToAnalyze.get(tempLetter);
                mapTextToAnalyze.put(tempLetter,k + 1);
            }
            else{
                mapTextToAnalyze.put(tempLetter, 1.0);
            }

        }
        mapTextToAnalyze = LetterContainsPercentage(mapTextToAnalyze,contentToAnalyze.length());
        temporary2 = ReplaceKeyAndValue(mapTextToAnalyze);
        var newMap2 = new TreeMap(Collections.reverseOrder());
        newMap2.putAll(temporary2);



        for (int i = 0; i < contentToBeDecrypted.length(); i++) {
            Character charTemp = contentToBeDecrypted.charAt(i);
            double doubleTemp = mapOfEncryptedText.get(charTemp);
            Character res = getCharacterWithNearestValue(doubleTemp, temporary2);
            if(i % 70 == 0)
                result.append("\n");
            result.append(res);
        }
        return result.toString();
    }



    // Key -> Value
    // Value -> Key
    private static TreeMap<Double, Character> ReplaceKeyAndValue(TreeMap<Character, Double> replaceKeyAndValue) {
        var replacedMap = new TreeMap<Double, Character>();

        for (Map.Entry<Character, Double> characterDoubleEntry : replaceKeyAndValue.entrySet()) {
            replacedMap.put(characterDoubleEntry.getValue(), characterDoubleEntry.getKey());
        }
        return replacedMap;
    }



    private Character getCharacterWithNearestValue(Double key, Map<Double, Character> mapToFindIn) {
        double temp;
        double result = Double.MAX_VALUE;
        double j = 0.0;
        for (Double aDouble : mapToFindIn.keySet()) {
            temp = Math.abs(key - aDouble);
            if (temp < result) {
                result = temp;
                j = aDouble;
            }
        }
        return mapToFindIn.get(j);
    }


    // Transform TreeMap amount VALUE, to percentage VALUE, KEY - same
    private TreeMap<Character, Double> LetterContainsPercentage(TreeMap<Character, Double> toModify, int size) {
        TreeMap<Character, Double> temporary = new TreeMap<>(toModify);

        for (Map.Entry<Character, Double> characterDoubleEntry : temporary.entrySet()) {
            Character t = characterDoubleEntry.getKey();
            Double z = characterDoubleEntry.getValue();
            z = (z * 100) / size;
            toModify.put(t, z);
        }
        return toModify;
    }
}
