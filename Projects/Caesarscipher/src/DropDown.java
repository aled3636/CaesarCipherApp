import javax.swing.*;
import java.io.File;


public class DropDown {
    private String[] optionsToChoose = {"Decrypt Text File",
            "Encrypt Text File", "Brute Force Text File", "Analyze encryption in Text Files", ""};

    private ReadAndWriteToOrFromFile readAndWriteToOrFromFile = new ReadAndWriteToOrFromFile();
    private AnalyzeContent analyzeContent = new AnalyzeContent();


    public void Start() {
        String getChose = (String) JOptionPane.showInputDialog(
                null,
                "What you decided to do?",
                "Encryption",
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionsToChoose,
                optionsToChoose[4]);

        String filePathString;
        switch (getChose) {
            case "Encrypt Text File": {

                // Open dialog boxes to:
                // -> open file and get encryption key from user
                int encryptionKeyChosenByUser = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Write encryption key:"));

                int choosesOption = JOptionPane.showConfirmDialog(null,
                        "Open file to encrypt", "Open File", JOptionPane.OK_CANCEL_OPTION);


                // If cancel button pressed
                // -> program close
                if (choosesOption == JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                // If file approved -> ReadFileToString -> EncryptString
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathString = selectedFile.toString();

                    String readFileString = readAndWriteToOrFromFile.ReadFileToString(filePathString);
                    String encryptedFileString = CaesarsCipher.EncryptContentByCaesarCipher(readFileString, encryptionKeyChosenByUser);

                    JTextArea textArea = new JTextArea(20, 50);
                    textArea.setText(encryptedFileString);
                    textArea.setEditable(false);

                    // wrap a scrollpane around textArea
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    // display them in a message dialog
                    JOptionPane.showMessageDialog(null, scrollPane);

                    JOptionPane.showConfirmDialog(null,
                            "Save text to File ? ", "Save", JOptionPane.OK_CANCEL_OPTION);

                    // Result write in file
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new File("./"));
                    int actionDialog = chooser.showSaveDialog(null); //where the dialog should render
                    if (actionDialog == JFileChooser.APPROVE_OPTION) {
                        var fileName = new File(chooser.getSelectedFile() + ".encrypted.txt");

                        //if filename already exists
                        if (fileName.exists()) {


                            actionDialog = JOptionPane.showConfirmDialog(null,
                                    "Replace existing file?", "Replace?", JOptionPane.YES_NO_OPTION);

                            if (actionDialog == JOptionPane.NO_OPTION)
                                return;
                            else {
                                JOptionPane.showMessageDialog(null, "File Replaced", "Replaced", JOptionPane.PLAIN_MESSAGE);
                                readAndWriteToOrFromFile.WriteStringToFile(fileName, encryptedFileString);
                            }


                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "File Created", "File Created", JOptionPane.PLAIN_MESSAGE);
                            readAndWriteToOrFromFile.WriteStringToFile(fileName, encryptedFileString);
                        }
                    }
                }
                break;
            }


            case "Decrypt Text File": {

                int decryptionKeyChosenByUser = Integer.parseInt(JOptionPane.showInputDialog(null,
                        "Write decryption key:"));

                int choosesOption = JOptionPane.showConfirmDialog(null,
                        "Open file to decrypt", "Open File", JOptionPane.OK_CANCEL_OPTION);

                if (choosesOption == JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathString = selectedFile.toString();

                    String readFileString = readAndWriteToOrFromFile.ReadFileToString(filePathString);
                    String encryptedFileString = CaesarsCipher.DecryptContentByCaesarsCipher(readFileString, decryptionKeyChosenByUser);

                    JTextArea textArea = new JTextArea(20, 50);
                    textArea.setText(encryptedFileString);
                    textArea.setEditable(false);

                    // wrap a scrollpane around it
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    // display them in a message dialog
                    JOptionPane.showMessageDialog(null, scrollPane);
                    JOptionPane.showConfirmDialog(null, "Save text to File ? ", "Save", JOptionPane.OK_CANCEL_OPTION);

                    JFileChooser chooser = new JFileChooser();

                    //where the dialog should render
                    chooser.setCurrentDirectory(new File("./"));

                    int actionDialog = chooser.showSaveDialog(null);
                    if (actionDialog == JFileChooser.APPROVE_OPTION) {
                        var fileName = new File(chooser.getSelectedFile() + ".decrypted.txt");
                        if (fileName.exists()) //if filename already exists
                        {
                            actionDialog = JOptionPane.showConfirmDialog(null,
                                    "Replace existing file?", "Replace?", JOptionPane.YES_NO_OPTION);
                            if (actionDialog == JOptionPane.NO_OPTION)
                                return;
                            else {
                                JOptionPane.showMessageDialog(null,
                                        "File Replaced", "Replaced", JOptionPane.PLAIN_MESSAGE);

                                readAndWriteToOrFromFile.WriteStringToFile(fileName, encryptedFileString);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "File Created", "File Created", JOptionPane.PLAIN_MESSAGE);

                            readAndWriteToOrFromFile.WriteStringToFile(fileName, encryptedFileString);
                        }
                    }
                }
                break;
            }


            case "Brute Force Text File": {

                int choosesOption = JOptionPane.showConfirmDialog(null,
                        "Open file to brute force text", "Open File", JOptionPane.OK_CANCEL_OPTION);

                if (choosesOption == JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }

                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = fileChooser.getSelectedFile();
                    filePathString = selectedFile.toString();

                    String readFileString = readAndWriteToOrFromFile.ReadFileToString(filePathString);
                    String hackedFileString = CaesarsCipher.BruteForce(readFileString);

                    JTextArea textArea = new JTextArea(20, 50);
                    textArea.setText(hackedFileString);
                    textArea.setEditable(false);

                    // wrap a scrollpane around it
                    JScrollPane scrollPane = new JScrollPane(textArea);

                    JOptionPane.showMessageDialog(null,
                            scrollPane);

                    JOptionPane.showConfirmDialog(null,
                            "Save text to File ? ", "Save", JOptionPane.OK_CANCEL_OPTION);

                    JFileChooser chooser = new JFileChooser();

                    chooser.setCurrentDirectory(new File("./"));
                    int actionDialog = chooser.showSaveDialog(null);

                    if (actionDialog == JFileChooser.APPROVE_OPTION) {
                        File fileName = new File(chooser.getSelectedFile() + ".hacked.txt");

                        if (fileName.exists()) {
                            actionDialog = JOptionPane.showConfirmDialog(null,
                                    "Replace existing file?", "Replace?", JOptionPane.YES_NO_OPTION);

                            if (actionDialog == JOptionPane.NO_OPTION)
                                return;
                            else {
                                JOptionPane.showMessageDialog(null,
                                        "File Replaced", "Replaced", JOptionPane.PLAIN_MESSAGE);

                                readAndWriteToOrFromFile.WriteStringToFile(fileName, hackedFileString);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "File Created", "File Created", JOptionPane.PLAIN_MESSAGE);

                            readAndWriteToOrFromFile.WriteStringToFile(fileName, hackedFileString);
                        }
                    }
                }
                break;
            }


            case "Analyze encryption in Text Files": {
                int choosesOption = JOptionPane.showConfirmDialog(null,
                        "Open two files to analyze text! \n" + "---------------------------------------\n" +
                                "First file with encrypted text! \n" + "---------------------------------------\n" +
                                "Second file with text to analyze!", "Open Files", JOptionPane.OK_CANCEL_OPTION);

                if (choosesOption == JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }

                JFileChooser firstChooser = new JFileChooser();
                JFileChooser secondChooser = new JFileChooser();
                int result = firstChooser.showOpenDialog(null);
                int result2 = secondChooser.showOpenDialog(null);


                if (result == JFileChooser.APPROVE_OPTION && result2 == JFileChooser.APPROVE_OPTION) {
                    File selectedFirstFile = firstChooser.getSelectedFile();
                    File selectedSecondFile = secondChooser.getSelectedFile();
                    String firstFilePathString = selectedFirstFile.toString();
                    String secondFilePathString = selectedSecondFile.toString();


                    // Read Files -> send DecryptTextByAnalyze
                    String readFirstFileString = readAndWriteToOrFromFile.ReadFileToString(firstFilePathString);
                    String readSecondFileString = readAndWriteToOrFromFile.ReadFileToString(secondFilePathString);
                    String analyzedFilesString = analyzeContent.DecryptTextByAnalyze(readFirstFileString, readSecondFileString);

                    JTextArea textArea = new JTextArea(20, 50);
                    textArea.setText(analyzedFilesString);
                    textArea.setEditable(false);

                    // wrap a scrollpane around it
                    JScrollPane scrollPane = new JScrollPane(textArea);


                    // display them in a message dialog
                    JOptionPane.showMessageDialog(null,
                            scrollPane);

                    JOptionPane.showConfirmDialog(null,
                            "Save text to File ? ", "Save", JOptionPane.OK_CANCEL_OPTION);

                    JFileChooser chooser = new JFileChooser();

                    //where the dialog should render
                    chooser.setCurrentDirectory(new File("./"));
                    int actionDialog = chooser.showSaveDialog(null);
                    if (actionDialog == JFileChooser.APPROVE_OPTION) {
                        File fileName = new File(chooser.getSelectedFile() + ".analyzed.txt");
                        if (fileName.exists()) {
                            actionDialog = JOptionPane.showConfirmDialog(null,
                                    "Replace existing file?", "Replace?", JOptionPane.YES_NO_OPTION);

                            if (actionDialog == JOptionPane.NO_OPTION)
                                return;
                            else {
                                JOptionPane.showMessageDialog(null,
                                        "File Replaced", "Replaced", JOptionPane.PLAIN_MESSAGE);

                                readAndWriteToOrFromFile.WriteStringToFile(fileName, analyzedFilesString);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "File Created", "File Created", JOptionPane.PLAIN_MESSAGE);

                            readAndWriteToOrFromFile.WriteStringToFile(fileName, analyzedFilesString);
                        }
                    }
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + getChose);
        }
    }
}