package luzhanov.mloparser;

import luzhanov.mloparser.model.TaskModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Tool for converting of MLO (MyLifeOrganized) XML-exported tasks to the plaintext.
 * Title & description will be present in the result.
 * Output is printed in the console.
 *
 * @author Igor Luzhanov, 14.05.2014
 */
public class MloTasksToPlaintextConverter {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("Please provide filepath");
        }

        String pathname = args[0];
        if (pathname == null || pathname.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide filepath");
        }

        File file = new File(pathname);

        try {
            List<TaskModel> mloTaskList = MloTaskParser.parseTasks(file);

            for (TaskModel taskModel : mloTaskList) {
                System.out.println(taskModel.toString());
            }

            System.out.println("\nTotal count: " + mloTaskList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}