import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.Thread.sleep;

public class MatrixHandlerTest {

    private static Logger log = Logger.getLogger(MatrixHandlerTest.class);
    private static CustomLogger customLogger = CustomLogger.getLogger();

    @DataProvider
    public Object[][] matrixMultiplyTestData() {
        return new Object[][]{
                {3, 1}, //1 поток
                {3, 3},  //3 поток
                {3, 10} //10 поток
                //, {50, 250}  //250 поток
        };
    }

    @Test(description = "", dataProvider = "matrixMultiplyTestData")
    public void matrixMultiplyTest(int dimension, int threadsCount) {
        String startLog = " ------ matrixMultiplyTest start " + "(dimension " + dimension
                + ", threadsCount "  + threadsCount + ") ------ ";
        try {
            sleep(5000);                                //Ожидание, чтобы дать другим потокам поработать
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        customLogger.info(startLog);
        log.info(startLog);
        MatrixHandler matrixHandler = new MatrixHandler(dimension);
        try {
        Assert.assertEquals(
                matrixHandler.matrixMultiply(),
                matrixHandler.matrixMultiplyWithThreads(threadsCount)
        );
        } catch (Throwable e) {
            e.printStackTrace();
            log.error("FAILED: matrixMultiplyTest ", e);
            Assert.fail();
        }
        String endLog = "----------------------matrixMultiplyTest finished----------------------";
        log.info(endLog);
        customLogger.info(endLog);
   }
}
