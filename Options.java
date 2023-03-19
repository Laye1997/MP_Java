import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Options {

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



    private boolean verbose;
    private boolean compress;
    private boolean decompress;
    private String inputFilePath;
    private String outputFilePath;
    private String outputDirectoryPath;

    // Constructeur(s)
    public Options(boolean verbose, boolean compress, boolean decompress, String inputFilePath, String outputFilePath, String outputDirectoryPath) {
        this.verbose = verbose;
        this.compress = compress;
        this.decompress = decompress;
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.outputDirectoryPath = outputDirectoryPath;
    }

    // Getters et setters
    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public boolean isDecompress() {
        return decompress;
    }

    public void setDecompress(boolean decompress) {
        this.decompress = decompress;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public String getOutputDirectoryPath() {
        return outputDirectoryPath;
    }

    public void setOutputDirectoryPath(String outputDirectoryPath) {
        this.outputDirectoryPath = outputDirectoryPath;
    }
}
