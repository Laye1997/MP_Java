import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;

public class Compression {
    // Attributs
    private String compressionAlgorithm;

    // Constructeur
    public Compression(String compressionAlgorithm) {
        this.compressionAlgorithm = compressionAlgorithm;
    }

    // Méthode de compression
    public boolean compressFile(String inputFilePath, String outputFilePath) {
        try {
            // Ouverture des flux d'entrée et de sortie
            FileInputStream fileInputStream = new FileInputStream(inputFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);

            // Compression du flux d'entrée vers le flux de sortie
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(fileOutputStream);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) > 0) {
                deflaterOutputStream.write(buffer, 0, length);
            }

            // Fermeture des flux
            deflaterOutputStream.close();
            fileOutputStream.close();
            fileInputStream.close();

            // Suppression du fichier original
            File inputFile = new File(inputFilePath);
            if (!inputFile.delete()) {
                System.err.println("Erreur : impossible de supprimer le fichier d'entrée.");
            }

            return true;
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
            return false;
        }
    }

}
