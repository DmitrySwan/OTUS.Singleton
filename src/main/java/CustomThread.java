import org.apache.log4j.Logger;

public class CustomThread extends Thread {

    private final Cell synchronizedObject;

    private int dimension;

    private int[][] firstMatrix;
    private int[][] secondMatrix;
    private int[][] resultMatrix;

    private static Logger log = Logger.getLogger(CustomThread.class);
    private static CustomLogger customLogger = CustomLogger.getLogger();

    CustomThread(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix, Cell synchronizedObject,
                 int dimension) {
        this.resultMatrix = resultMatrix;
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.synchronizedObject = synchronizedObject;
        this.dimension = dimension;
    }

    @Override
    public void run() {
        log.info("#Thread " + this.getName() + " is running.");
        while (!(synchronizedObject.getCurrentI() == dimension)) {

            Cell unprocessedCell;
            synchronized (synchronizedObject) {                 //критическая секция
                unprocessedCell = new Cell(synchronizedObject);
                updateNextUnprocessedElement();
            }

            log.info(this.getName() + " is processing the cell: " + unprocessedCell);
            customLogger.info(this.getName(), unprocessedCell.toString());

            for (int k = 0; k < dimension; k++) {
                resultMatrix[unprocessedCell.getCurrentI()][unprocessedCell.getCurrentJ()] +=
                        firstMatrix[unprocessedCell.getCurrentI()][k] * secondMatrix[k][unprocessedCell.getCurrentJ()];
            }
            try {
                sleep(1000);                                //Ожидание, чтобы дать другим потокам поработать
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("#Thread " + this.getName() + " finished.");
    }

    private void updateNextUnprocessedElement() {
        int j = synchronizedObject.getCurrentJ();
        if (j < dimension - 1) {                                                //Если j < N , то j + 1
            synchronizedObject.setCurrentJ(j + 1);
            return;
        }
        synchronizedObject.setCurrentJ(0);                                       //Иначе j = 0, i + 1
        synchronizedObject.setCurrentI(synchronizedObject.getCurrentI() + 1);
    }
}
