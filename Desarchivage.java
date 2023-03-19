import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Desarchivage {

    private static final String ARCHIVE_FILE_NAME = "tp.rar";
    private static final String FILE1_NAME = "file1.txt";
    private static final String FILE2_NAME = "file2.txt";
    private static final String OUTPUT_DIRECTORY_PATH = "C:/Users/Acer/Desktop/ESP/jProjetFinal/Projetfinal/src/";

    private String inputFilePath;
    private String outputDirectoryPath;
    private ZipEntry zipEntry;

    public Desarchivage(String inputFilePath, String outputDirectoryPath) {
        this.inputFilePath = inputFilePath;
        this.outputDirectoryPath = outputDirectoryPath;
    }

    public void unarchive() {
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFilePath);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);

            byte[] buffer = new byte[1024];
            int count;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                if (fileName.equals(FILE1_NAME) || fileName.equals(FILE2_NAME)) {
                    FileOutputStream fileOutputStream = new FileOutputStream(outputDirectoryPath + fileName);
                    while ((count = zipInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
                zipInputStream.closeEntry();
            }
            zipInputStream.close();
        } catch (IOException e) {
            System.out.println("Erreur lors du désarchivage du fichier : " + e.getMessage());
        }
    }

    public void setArchiveFilePath(String archiveFilePath) {
        this.inputFilePath = archiveFilePath;
    }

    public void setOutputDirectoryPath(String outputDirectoryPath) {
        this.outputDirectoryPath = outputDirectoryPath;
    }

    public boolean hasArchive() {
        return inputFilePath != null;
    }

    public boolean hasOutputDirectory() {
        return outputDirectoryPath != null;
    }

    public String getArchiveFilePath() {
        return inputFilePath;
    }

    public String getOutputDirectoryPath() {
        return outputDirectoryPath;
    }
}