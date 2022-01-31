import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadAndWriteToOrFromFile {


    // Reads File into String;
    // IN: Path of text File;
    // OUT: String with File content.
    public String ReadFileToString(String pathOfTextFileString) {
        Path pathOfTextFile = Path.of(pathOfTextFileString);
        try {
            return Files.readString(pathOfTextFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    // Writes File into String;
    // IN: Path of text File and String content
    // OUT: Nothing void, File Created
    public  void WriteStringToFile(File file, String content){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
