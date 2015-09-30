package musichelper;

import musichelper.model.DuplicateFolderModel;
import musichelper.model.FolderModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Hello world!
 */
public class App {

    public static final String DELIMITER = "\\";

    public static void main(String[] args) throws Exception {
        //  System.out.println( "Hello World!" );
        String rootPath = "D:\\!Music_discs";
        Collection<String> firstLevel = FileUtils.getSubFolders(rootPath);
        ArrayList<FolderModel> artistLevelList = new ArrayList<FolderModel>();

        for (String currSubdir : firstLevel) {
            String pathToDir = rootPath + DELIMITER + currSubdir;
            Collection<String> subDirectories = FileUtils.getSubFolders(pathToDir);

            for (String seconsLevelDir : subDirectories) {
                artistLevelList.add(new FolderModel(pathToDir + DELIMITER + seconsLevelDir, seconsLevelDir));
            }
        }

        //LoggingUtils.logCollection(artistLevelList);

        Collection<DuplicateFolderModel> duplicates = MusicLogicUtils.locateDuplicates(artistLevelList);

        LoggingUtils.logCollection(duplicates);

        //==copy to new folder==
//        String copyPath = "D:\\TMP\\!Music_discs_moved";
//
//        for (DuplicateFolderModel currDup : duplicates) {
//            for (FolderModel currFolder : currDup.getDuplicateList()) {
//                FileUtils.moveFolder(new File(currFolder.getFullPath()), new File(copyPath + DELIMITER + currFolder.getName()));
//            }
//        }

    }
}
