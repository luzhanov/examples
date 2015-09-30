package musichelper;

import musichelper.model.DuplicateFolderModel;
import musichelper.model.FolderModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author luzhanov
 */
public class MusicLogicUtils {

    public static Collection<DuplicateFolderModel> locateDuplicates(List<FolderModel> folders) {
        HashMap<String, DuplicateFolderModel> duplicatesMap = new HashMap<String, DuplicateFolderModel>();

        for (FolderModel currFolder : folders) {
            String currFolderName = currFolder.getName();
            DuplicateFolderModel duplicateModel = duplicatesMap.get(currFolderName);

            //skip if we already process this name
            if (duplicateModel != null) {
                continue;
            }

            duplicateModel = new DuplicateFolderModel(currFolderName);
            for (FolderModel folder2 : folders) {
                if (currFolderName.equals(folder2.getName()) && currFolder != folder2) {
                    duplicateModel.getDuplicateList().add(folder2);
                }
            }
            if (duplicateModel.getDuplicateList().size() > 1) {
                duplicatesMap.put(currFolderName, duplicateModel);
            }
        }

        return duplicatesMap.values();
    }

}
