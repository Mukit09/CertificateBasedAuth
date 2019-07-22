import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;

@Slf4j
public class CloserUtility {
    private static volatile CloserUtility closerUtility;
    private CloserUtility() {}

    public static CloserUtility getInstance() {
        if(closerUtility == null) {
            synchronized (CloserUtility.class) {
                if(closerUtility == null) {
                    closerUtility = new CloserUtility();
                }
            }
        }
        return closerUtility;
    }

    public void closeFileInputStream(FileInputStream inputStream) {
        try {
            if(inputStream != null) inputStream.close();
        } catch (Exception ex) {
            log.error("Exception: ", ex);
        }
    }

    public void closeFileOutputStream(FileOutputStream outputStream) {
        try {
            if(outputStream != null) outputStream.close();
        } catch (Exception ex) {
            log.error("Exception: ", ex);
        }
    }
}
