import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class SenFileCompressor {
    public static void main(String[] args) {
        // Vérification du nombre d'arguments
        if (args.length < 1 || args.length > 7) {
            printHelp();
            return;
        }

        // Analyse des options et des arguments
        boolean compress = false;
        boolean decompress = false;
        String inputFile = null;
        String outputDir = null;
        boolean createOutputDir = false;
        boolean verbose = false;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-c")) {
                compress = true;
                inputFile = args[++i];
            } else if (arg.equals("-d")) {
                decompress = true;
                inputFile = args[++i];
            } else if (arg.equals("-r")) {
                outputDir = args[++i];
            } else if (arg.equals("-f")) {
                createOutputDir = true;
            } else if (arg.equals("-v")) {
                verbose = true;
            } else if (arg.equals("-h")) {
                printHelp();
                return;
            } else {
                System.out.println("Option invalide : " + arg);
                printHelp();
                return;
            }
        }

        // Vérification de la cohérence des options
        if ((compress && decompress) || (!compress && !decompress)) {
            System.out.println("Il faut spécifier une et une seule des options -c ou -d");
            printHelp();
            return;
        }
        if (createOutputDir && outputDir == null) {
            System.out.println("L'option -f doit être utilisée avec l'option -r");
            printHelp();
            return;
        }

        // Exécution de la commande correspondante
        try {
            if (compress) {
                File inputFileObject = new File(inputFile);
                String outputFileName = inputFileObject.getName() + ".compressed";
                String outputFilePath = getOutputFilePath(outputDir, outputFileName);
                compress(inputFile, outputFilePath);
                System.out.println("Compression réussie! Le fichier compressé se trouve à l'emplacement : " + outputFilePath);
            } else {
                File inputFileObject = new File(inputFile);
                String outputFileName = inputFileObject.getName().replace(".compressed", "");
                String outputFilePath = getOutputFilePath(outputDir, outputFileName);
                decompress(inputFile, outputFilePath);
                System.out.println("Décompression réussie! Le fichier décompressé se trouve à l'emplacement : " + outputFilePath);
            }
        } catch (IOException e) {
            System.out.println("Une erreur est survenue lors de la compression/décompression du fichier : " + e.getMessage());
        }
    }

    public static void compress(String inputFilePath, String outputFilePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputFilePath);
             FileOutputStream outputStream = new FileOutputStream(outputFilePath);
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                gzipOutputStream.write(buffer, 0, len);
            }
            gzipOutputStream.finish();
        }
    }

    public static void decompress(String inputFilePath, String outputFilePath) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputFilePath);
             GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
             FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        }
    }


    private static String getOutputFilePath(String outputDir, String outputFileName) {
        String outputFilePath = outputFileName;
        if (outputDir != null) {
            File outputDirObject = new File(outputDir);
            if (!outputDirObject.exists()) {
                outputDirObject.mkdirs();
            }
            outputFilePath = outputDir + File.separator + outputFileName;
        }
        return outputFilePath;
    }

    private static void printHelp() {
        System.out.println("Usage : java SenFileCompressor [-c|-d] [-r outputDir] [-f] [-v] [-h] <file1> [file2] ...");
        System.out.println("Compresse ou décompresse les fichiers spécifiés");
        System.out.println();
        System.out.println("Options :");
        System.out.println("    -c             compresse le fichier spécifié");
        System.out.println("    -d             décompresse le fichier spécifié");
        System.out.println("    -r outputDir   enregistre le résultat dans le répertoire spécifié (par défaut, le répertoire courant)");
        System.out.println("    -f             crée le répertoire de sortie s'il n'existe pas");
        System.out.println("    -v             affiche des informations détaillées sur l'opération");
        System.out.println("    -h             affiche cette aide");
        System.out.println();
        System.out.println("Exemples :");
        System.out.println("    java SenFileCompressor -c file1.txt");
        System.out.println("        compresse le fichier file1.txt dans le répertoire courant");
        System.out.println("    java SenFileCompressor -c -r C:\\output file1.txt");
        System.out.println("        compresse le fichier file1.txt et enregistre le résultat dans le répertoire C:\\output");
        System.out.println("    java SenFileCompressor -d -f -v C:\\input\\*.gz");
        System.out.println("        décompresse tous les fichiers .gz dans le répertoire C:\\input, en créant le répertoire de sortie s'il n'existe pas et en affichant des informations détaillées");
    }
}