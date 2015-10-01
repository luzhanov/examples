package luzhanov.mloparser;

import luzhanov.mloparser.model.TaskModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author Igor Luzhanov, 15.05.2014
 */
public class MloTaskParser {

    public static List<TaskModel> parseTasks(File file) throws Exception {
        ArrayList<TaskModel> resultList = new ArrayList<>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        //optional, but recommended
        //read - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("TaskNode");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                TaskModel newModel = parseTaskElement((Element) nNode);
                resultList.add(newModel);
            }
        }

        return resultList;
    }

    private static TaskModel parseTaskElement(Element nNode) {
        TaskModel newModel = new TaskModel();

        newModel.setCaption(nNode.getAttribute("Caption").trim());
        NodeList note = nNode.getElementsByTagName("Note");
        if (note != null && !(note.getLength() == 0)) {
            Node noteOne = note.item(0);
            if (noteOne != null) {
                String noteText = noteOne.getTextContent();
                if (noteText != null && !noteText.trim().isEmpty()) {
                    newModel.setNote(noteText.trim());
                }
            }
        }
        return newModel;
    }

}
