package musichelper.model;

/**
 * @author luzhanov
 */
public class FolderModel {

    private String fullPath;
    private String name;

    public FolderModel() {
    }

    public FolderModel(String fullPath, String name) {
        this.fullPath = fullPath;
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FolderModel{" +
                "name='" + name + '\'' +
                ", fullPath='" + fullPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FolderModel that = (FolderModel) o;

        if (!fullPath.equals(that.fullPath)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fullPath.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
