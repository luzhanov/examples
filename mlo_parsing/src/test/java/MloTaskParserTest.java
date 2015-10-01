import luzhanov.mloparser.MloTaskParser;
import luzhanov.mloparser.model.TaskModel;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Igor Luzhanov, 14.05.2014
 */
public class MloTaskParserTest {

    @Test
    public void testParsing() throws Exception {
        File file = new File(this.getClass().getResource("test.xml").getPath());

        List<TaskModel> tasks = MloTaskParser.parseTasks(file);
        assertThat(tasks).hasSize(5);

        assertThat(tasks.get(3).getCaption()).isEqualTo("Michael Kenna");
        assertThat(tasks.get(3).getNote()).isEqualTo("http://www.michaelkenna.net/imagearchive.php");

        assertThat(tasks.get(4).getCaption()).isEqualTo("прочитать книгу");
    }

}
