import javax.swing.*;


public class CaesarsCipher {
    private static final String ALPHABET
            = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ.;:!?-, 0123456789\n";


    // Encrypting content;
    // IN: Content String and cipher key int;
    // OUT: String with encrypted content.
    public static String EncryptContentByCaesarCipher(String contentToBeEncrypted, int encryptionKey) {
        char charToBeEncrypted;
        char encrypted;
        int indexOfCharToBeEncryptedInAlphabet;
        StringBuilder encryptedContent = new StringBuilder();

        if (encryptionKey == 0)
            return contentToBeEncrypted;

        for (int i = 0; i < contentToBeEncrypted.length(); i++) {

            charToBeEncrypted = contentToBeEncrypted.charAt(i);
            indexOfCharToBeEncryptedInAlphabet = ALPHABET.indexOf(charToBeEncrypted);
            encrypted = ALPHABET.charAt((indexOfCharToBeEncryptedInAlphabet + encryptionKey) % ALPHABET.length());

            if (i % 75 == 0) {
                encryptedContent.append("\n");
            }
            encryptedContent.append(encrypted);
        }
        return encryptedContent.toString();
    }

    // Decrypting content;
    // IN: Content String and cipher key int;
    // OUT: String with decrypted content.
    public static String DecryptContentByCaesarsCipher(String contentToBeDecrypted, int decryptionKey) {
        char charToBeDecrypted;
        char decrypted;
        int indexOfCharToBeDecryptedInAlphabet;
        StringBuilder decryptedContent = new StringBuilder();
        decryptionKey = decryptionKey % ALPHABET.length();


        if (decryptionKey == 0)
            return contentToBeDecrypted;


        for (int i = 0; i < contentToBeDecrypted.length(); i++) {
            charToBeDecrypted = contentToBeDecrypted.charAt(i);
            indexOfCharToBeDecryptedInAlphabet = ALPHABET.indexOf(charToBeDecrypted);

            if (indexOfCharToBeDecryptedInAlphabet - decryptionKey < 0) {
                int temp = (indexOfCharToBeDecryptedInAlphabet - decryptionKey);
                decrypted = ALPHABET.charAt(ALPHABET.length() + temp);

            } else {
                decrypted = ALPHABET.charAt(indexOfCharToBeDecryptedInAlphabet - decryptionKey);
            }
            decryptedContent.append(decrypted);
        }
        return decryptedContent.toString();
    }


    // Decrypting content via brute Force
    public static String BruteForce(String contentToBeDecrypted) {
        for (int i = 0; i < ALPHABET.length(); i++) {
            String temp = DecryptContentByCaesarsCipher(contentToBeDecrypted, i);
            if (IsTextValid(temp)) {
                JOptionPane.showMessageDialog(null, "Encryption key for this text is: " + i);
                return temp;
            }
        }
        return "Program can not decrypt text";
    }


    // Checking text validation
    private static Boolean IsTextValid(String contentToBeChecked) {
        int indexOfDot = contentToBeChecked.indexOf(".");
        int indexOfSpace = contentToBeChecked.indexOf(" ");
        if (indexOfSpace < 0 || indexOfSpace > 25) {
            return false;
        } else return indexOfDot >= 0 && contentToBeChecked.charAt(indexOfDot + 1) == ' ';
    }
}


