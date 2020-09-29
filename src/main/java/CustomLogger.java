import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomLogger {
    private static CustomLogger instance;

    private File logFile;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String INFO = "INFO";

    static CustomLogger getLogger() {
        if (instance == null) {
            instance = new CustomLogger();
        }
        return instance;
    }

    private CustomLogger() {
        try {
            logFile = new File("./target/CustomMatrixMultiplyLogFile.log");
            if (logFile.createNewFile()) {
                System.out.println("File created: " + logFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void info(String threadName, String cellInfo) {
        String threadWorkLog = String.format("%s %s  %s  is processing the %s",
                simpleDateFormat.format(new Date()), INFO, threadName, cellInfo);
        info(threadWorkLog);
    }

    void info(String text) {
        try(FileWriter fw = new FileWriter(logFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
