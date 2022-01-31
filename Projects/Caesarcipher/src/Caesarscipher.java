import com.sun.jdi.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Caesarscipher {
    private static final String ALPHABET
            = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.;:!?-, 0123456789\n";

    public static void main(String[] args) {
        String w = ReadFileToString("C:\\Projects\\Caesarcipher\\Test.txt");
        String z = ReadFileToString("C:\\Projects\\Caesarcipher\\Test.txt");
        String b = EncryptContentByCaesarCipher(w, 2);
        //System.out.println(b);
        /*String h = DecryptContentByCaesarsCipher(b,2);
        System.out.println(h);*/
        //String q = BruteForce(b);
        //System.out.println(q);
        System.out.println(DecryptTextByAnalyze(b,z));

}
    // Reads File into String;
    // IN: Path of text File;
    // OUT: String with File content.
 private static String ReadFileToString(String pathOfTextFileString) {
     Path pathOfTextFile = Path.of(pathOfTextFileString);
     try {
         String fileContentToString = Files.readString(pathOfTextFile);
         return fileContentToString;
     } catch (IOException e) {
         e.printStackTrace();
     }
     return "";
 }


   // Encrypting content;
   // IN: Content String and cipher key int;
   // OUT: String with encrypted content.
 private static String EncryptContentByCaesarCipher(String contentToBeEncrypted, int encryptionKey){
        char charToBeEncrypted;
        char encrypted;
        int indexOfCharToBeEncryptedInAlphabet;
        String encryptedContent = "";

        if(encryptionKey == 0 )
            return contentToBeEncrypted;

        for (int i = 0; i <contentToBeEncrypted.length() ; i++) {
         charToBeEncrypted = contentToBeEncrypted.charAt(i);
         indexOfCharToBeEncryptedInAlphabet = ALPHABET.indexOf(charToBeEncrypted);
         encrypted = ALPHABET.charAt((indexOfCharToBeEncryptedInAlphabet + encryptionKey) % ALPHABET.length());
         encryptedContent = encryptedContent + encrypted;
     }
        return encryptedContent;
 }

    // Decrypting content;
    // IN: Content String and cipher key int;
    // OUT: String with decrypted content.
 private static String DecryptContentByCaesarsCipher(String contentToBeDecrypted, int decryptionKey){
     char charToBeDecrypted;
     char decrypted;
     int indexOfCharToBeDecryptedInAlphabet;
     String decryptedContent = "";
     decryptionKey = decryptionKey % ALPHABET.length();


     if(decryptionKey == 0 )
         return contentToBeDecrypted;


     for (int i = 0; i <contentToBeDecrypted.length() ; i++) {
         charToBeDecrypted = contentToBeDecrypted.charAt(i);
         indexOfCharToBeDecryptedInAlphabet = ALPHABET.indexOf(charToBeDecrypted);

         if(indexOfCharToBeDecryptedInAlphabet - decryptionKey < 0){
             int temp = (indexOfCharToBeDecryptedInAlphabet - decryptionKey);
             decrypted = ALPHABET.charAt(ALPHABET.length() + temp);
         }
         else {
             decrypted = ALPHABET.charAt(indexOfCharToBeDecryptedInAlphabet - decryptionKey);
         }
         decryptedContent = decryptedContent + decrypted;
     }

     return decryptedContent;
 }

   // Decrypting content via brute Force
 private static String BruteForce(String contentToBeDecrypted){
     for (int i = 0; i < ALPHABET.length(); i++) {
         String temp = DecryptContentByCaesarsCipher(contentToBeDecrypted,i);
         if(IsTextValid(temp)){
             System.out.println("Encryption key for this text is: " + i);
             return temp;
         }
     }
        return "Program can not decrypt text";
 }

    // Checking text validation
 private static Boolean IsTextValid(String contentToBeChecked){
     int indexOfDot = contentToBeChecked.indexOf(".");
     int indexOfSpace = contentToBeChecked.indexOf(" ");
         if(indexOfSpace < 0 || indexOfSpace > 25){
             return false;
         }
         else if(indexOfDot < 0 || contentToBeChecked.charAt(indexOfDot + 1) != ' '){
            return false;
         }
         else {
             return true;
         }
 }


 // Transform TreeMap amount VALUE, to percentage VALUE, KEY - same
     private static TreeMap<Character, Double> LetterContainsPercentage (TreeMap<Character,Double> toModify, int size){
     TreeMap<Character, Double> temporary = new TreeMap<>();
     temporary.putAll(toModify);
     for (Map.Entry<Character, Double> characterDoubleEntry : temporary.entrySet()) {
         Character t = characterDoubleEntry.getKey();
         Double z = characterDoubleEntry.getValue();
         z = (z * 100) /size;
         toModify.put(t,z);
     }
     return toModify;
 }


    //Analyze 2 Content By amount of letters in text
    //TreeMap in use
 private static String DecryptTextByAnalyze(String contentToBeDecrypted, String contentToAnalyze){
     //System.out.println(contentToBeDecrypted);
     var mapOfEncryptedText = new TreeMap<Character, Double>();
     var mapTextToAnalyze = new TreeMap<Character, Double>();
     var temporary = new TreeMap<Double, Character>();
     var temporary2 = new TreeMap<Double, Character>();
     String  result = "";

     //System.out.println(contentToAnalyze.length());

     contentToBeDecrypted = contentToBeDecrypted.toLowerCase();
     contentToAnalyze = contentToAnalyze.toLowerCase();

     //contentToAnalyze = contentToAnalyze.replaceAll("[^\\p{L}\\p{N}]+", "");   //Убирает все символы кроме букв

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
     //System.out.println(newMap);
     System.out.println();

  /*   mapOfEncryptedText.entrySet().stream().sorted((n1,n2)->n1.getValue().compareTo(n2.getValue())).forEach(n->System.out.println(n));
     System.out.println();*/                         //Stream. sort map by Value



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

     //System.out.println(newMap2);
     //mapTextToAnalyze.entrySet().stream().sorted((n1, n2)->n1.getValue().compareTo(n2.getValue())).forEach(n->System.out.println(n));
     for (Map.Entry<Character, Double> characterDoubleEntry : mapTextToAnalyze.entrySet()) {
         //System.out.println(characterDoubleEntry);
     }
     //System.out.println();
     //System.out.println(mapOfEncryptedText);
     //д=3.4482758620689653
     //3.6337812888837075


     for (int i = 0; i < contentToBeDecrypted.length(); i++) {
         Character charTemp = contentToBeDecrypted.charAt(i);
         //System.out.println("Берем букву : " + charTemp );
         double doubleTemp = mapOfEncryptedText.get(charTemp);
         //System.out.println("Получаем значение этой буквы в шифрованой мапе : " + doubleTemp);
         Character res = getCharacterWithNearestValue(doubleTemp, temporary2);
         //System.out.println("принеслась сюда эта буква : " + res);
         result = result + res;
         //System.out.println("как пополняется результ : " + result);

     }


     return result;
 }


    // Key -> Value
    // Value -> Key
 private static TreeMap<Double, Character> ReplaceKeyAndValue(TreeMap<Character, Double> replaceKeyAndValue){
        var replacedMap = new TreeMap<Double, Character>();
     for (Map.Entry<Character, Double> characterDoubleEntry : replaceKeyAndValue.entrySet()) {
         replacedMap.put(characterDoubleEntry.getValue(),characterDoubleEntry.getKey());
         //System.out.println("получили kljuch : " + characterDoubleEntry.getKey() +  "  poluchili Value : " + characterDoubleEntry.getValue());

     }
     for (Map.Entry<Double, Character> doubleCharacterEntry : replacedMap.entrySet()) {
         //System.out.println(doubleCharacterEntry.getKey() + " = " + doubleCharacterEntry.getValue());
     }
        return replacedMap;
 }


   private  static Character getCharacterWithNearestValue(Double key, Map<Double,Character> mapToFindIn){
        double temp;
        double result = Double.MAX_VALUE;
        double j = 0.0;
       for (Double aDouble : mapToFindIn.keySet()) {
           temp = Math.abs(key - aDouble);
           if (temp < result){
               result = temp;
               j = aDouble;

           }

       }


       System.out.println("наиближайшее значение к этому числу : " + j);
        char b = mapToFindIn.get(j);
       System.out.println(("буква на которую меняем : " + mapToFindIn.get(j)));
       //System.out.println(j);

        return b;
    }

}

