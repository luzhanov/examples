package musichelper;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author luzhanov
 */
public class LoggingUtils {

    public static void logArray(Object[] array) {
        for (Object curr : array) {
            System.out.println(curr);
        }
    }

    public static void logCollection(Collection col) {
        for (Object curr : col) {
            System.out.println(curr);
        }
    }

}
