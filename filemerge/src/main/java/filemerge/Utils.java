package filemerge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static FileOutputStream createFileOutputStream(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        return new FileOutputStream(file, false);
    }

    public static FileInputStream createFileInputStream(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + filename);
        }
        return new FileInputStream(file);
    }

    @SuppressWarnings("ConstantConditions")
    public static FileInputStream createFileInputStreamViaClassloader(String fileName) throws IOException {
        ClassLoader classLoader = Utils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        return new FileInputStream(file);
    }

}
