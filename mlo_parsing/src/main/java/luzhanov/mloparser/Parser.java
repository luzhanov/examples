package luzhanov.mloparser;

import luzhanov.mloparser.model.TaskModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Igor Luzhanov, 14.05.2014
 */
public class Parser {

    public static void main(String[] args) throws IOException {
        //String pathname = "test.xml";
        String pathname = "D:\\!Depot_personal\\videotagger\\mlo_parsing\\src\\main\\resources\\test.xml";
        File file = new File(pathname);

        try {
            List<TaskModel> taskList = TaskPlainParser.parseTasks(file);
            for (TaskModel taskModel : taskList) {
                System.out.println(taskModel.toString());
            }

            System.out.println("\ntotal: "+taskList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
