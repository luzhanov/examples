package musichelper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author luzhanov
 */
public class FileUtils {

    public static void listSubFolders(String pathToDir) {
        File file = new File(pathToDir);
        String[] directories = file.list((dir, name) -> new File(dir, name).isDirectory());
        LoggingUtils.logArray(directories);
    }

    public static Collection<String> getSubFolders(String pathToDir) {
        File file = new File(pathToDir);
        String[] directories = file.list((dir, name) -> new File(dir, name).isDirectory());

        return Arrays.asList(directories);
    }

    public static boolean fileExists(String path) {
        File f = new File(path);
        return f.exists();
    }

    public static void copyFolder(File src, File dest) throws IOException {
        copyFolder(src, dest, 0);
    }

    private static void copyFolder(File src, File dest, int level) throws IOException {
        if (dest == null) {
            throw new IllegalArgumentException("Dest is null");
        }

        if (src.isDirectory()) {
            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdir();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            } else if (level > 0 && dest.listFiles() != null && dest.listFiles().length > 0) {
                //if file already exists - create new folder with underscore
                dest = new File(dest.getPath() + "_");
                copyFolder(src, dest, ++level);
                return;
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile, destFile, ++level);
            }
        } else {
            //if file, then copy it
            org.apache.commons.io.FileUtils.copyFile(src, dest);
            System.out.println("File copied from " + src + " to " + dest);
        }
    }

    public static void moveFolder(File src, File dest) throws IOException {
        moveFolder(src, dest, 0);
    }

    private static void moveFolder(File src, File dest, int level) throws IOException {
        if (dest == null) {
            throw new IllegalArgumentException("Dest is null");
        }

        if (src.isDirectory()) {
            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdir();
                System.out.println("Directory copied from "
                        + src + "  to " + dest);
            } else if (level > 0  && dest.listFiles() != null && dest.listFiles().length > 0) {
                //if file already exists - create new folder with underscore
                dest = new File(dest.getPath() + "_");
                moveFolder(src, dest, ++level);
                return;
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                moveFolder(srcFile, destFile, ++level);
            }
            org.apache.commons.io.FileUtils.deleteDirectory(src);
        } else {
            //if file, then copy it
            org.apache.commons.io.FileUtils.moveFile(src, dest);
            System.out.println("File moved from " + src + " to " + dest);
        }
    }

}

