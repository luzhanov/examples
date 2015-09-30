package musichelper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luzhanov
 */
public class DuplicateFolderModel {

    private final String name;

    private List<FolderModel> duplicateList = new ArrayList<FolderModel>();

    public DuplicateFolderModel(String name) {
        this.name = name;
    }

    public List<FolderModel> getDuplicateList() {
        return duplicateList;
    }

    public void setDuplicateList(List<FolderModel> duplicateList) {
        this.duplicateList = duplicateList;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DuplicateFolderModel{" +
                "count=" + duplicateList.size() +
                " name='" + name + '\'' +
                ", duplicateList=" + duplicateList +
                '}';
    }
}
