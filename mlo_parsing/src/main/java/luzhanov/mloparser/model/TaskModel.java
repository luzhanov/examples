package luzhanov.mloparser.model;

/**
 * @author Igor Luzhanov, 15.05.2014
 */
public class TaskModel {

    private String caption;
    private String note;

    public TaskModel() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        if (caption.endsWith("...")) {
            this.caption = caption.substring(0, caption.length() - 3);
        } else {
            this.caption = caption;
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (note == null) {
            sb.append(caption);
        } else {
            if (note.contains(caption)) {
                sb.append(note);
            } else {
                sb.append(caption).append(" -> ").append(note);
            }
        }

        return sb.toString();
    }
}
