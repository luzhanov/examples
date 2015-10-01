package musichelper;

import musichelper.model.DuplicateFolderModel;
import musichelper.model.FolderModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class MusicHelper {

    public static final String DELIMITER = "\\";

    public static final String ROOT_PATH = "D:\\!Music_discs";
    public static final String COPY_PATH = "D:\\TMP\\!Music_discs_moved";

    public static void main(String[] args) throws Exception {
        //todo: get rootpath from command line

        Collection<String> firstLevel = FileUtils.getSubFolders(ROOT_PATH);
        ArrayList<FolderModel> artistLevelList = new ArrayList<>();

        for (String currSubdir : firstLevel) {
            String pathToDir = ROOT_PATH + DELIMITER + currSubdir;
            Collection<String> subDirectories = FileUtils.getSubFolders(pathToDir);

            for (String seconsLevelDir : subDirectories) {
                artistLevelList.add(new FolderModel(pathToDir + DELIMITER + seconsLevelDir, seconsLevelDir));
            }
        }

        //LoggingUtils.logCollection(artistLevelList);

        Collection<DuplicateFolderModel> duplicates = MusicLogicUtils.locateDuplicates(artistLevelList);

        LoggingUtils.logCollection(duplicates);

        //todo: get copy path from command line
        String copyPath = COPY_PATH;

        copyToNewFolder(copyPath, duplicates);
    }

    private static void copyToNewFolder(String copyPath, Collection<DuplicateFolderModel> duplicates) throws IOException {
        for (DuplicateFolderModel currDup : duplicates) {
            for (FolderModel currFolder : currDup.getDuplicateList()) {
                FileUtils.moveFolder(new File(currFolder.getFullPath()), new File(copyPath + DELIMITER + currFolder.getName()));
            }
        }
    }

}
