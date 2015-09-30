package luzhanov.mloparser;

import luzhanov.mloparser.model.TaskModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Luzhanov, 15.05.2014
 */
public class TaskPlainParser {


    public static List<TaskModel> parseTasks(File file) throws Exception {
        ArrayList<TaskModel> resultList = new ArrayList<TaskModel>();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("TaskNode");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
           // System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                TaskModel newModel = new TaskModel();
                newModel.setCaption(eElement.getAttribute("Caption").trim());
                NodeList note = eElement.getElementsByTagName("Note");
                if (note != null && !(note.getLength() == 0)) {
                    Node noteOne = note.item(0);
                    if (noteOne != null) {
                        String noteText = noteOne.getTextContent();
                        if (noteText != null && !noteText.trim().isEmpty()) {
                            newModel.setNote(noteText.trim());
                        }
                    }
                }

                resultList.add(newModel);
            }
        }


        return resultList;
    }

}
