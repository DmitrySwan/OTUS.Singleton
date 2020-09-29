import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MatrixHandler {

    private static Logger log = Logger.getLogger(MatrixHandler.class);
    private Calendar calendar = Calendar.getInstance();

    private int dimension;
    private int[][] firstMatrix, secondMatrix;

    public MatrixHandler(int dimension) {
        this.dimension = dimension;
        this.firstMatrix = generateRandomMatrix();
        this.secondMatrix = generateRandomMatrix();
    }

    /**
     * Метод генерации матрицы с случайными целыми полоительными числами в диапазоне 1 - 10
     * @return  int[][] - матрица, результат генерации
     **/
    private int[][] generateRandomMatrix() {

        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = new Random().nextInt(10 - 1);
            }
        }
        log.info("#Generated matrix \n" + printMatrix(matrix));
        return matrix;
    }

    /**
     * Метод простого перемножения матриц
     * @return  int[][] - матрица, результат перемножения
     **/
    int[][] matrixMultiply() {
        int[][] resultMatrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                for (int k = 0; k < dimension; k++) {
                    resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        log.info("#Simple Result(without multi-threading) \n" + printMatrix(resultMatrix));
        return resultMatrix;
    }

    /**
     * Метод для перемножения матриц с использованием потоков
     * @param int threadsCount - количество потоков
     * @return  int[][] - матрица, результат перемножения
     **/
    int[][] matrixMultiplyWithThreads(int threadsCount)
            throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List<CustomThread> listThreads = new LinkedList<>();

        int[][] resultMatrix = new int[dimension][dimension];

        Cell synchronizedObject = new Cell();

        for (int i = 0; i < threadsCount; i++) {
            listThreads.add(new CustomThread(firstMatrix, secondMatrix, resultMatrix, synchronizedObject, dimension));
        }
        for (int i = 0; i < threadsCount; i++) {
            listThreads.get(i).start();
        }
        for (int i = 0; i < threadsCount; i++) {
            listThreads.get(i).join(); //даем всем потокам возможность закончить выполнение перед тем,
                                       // как программа (главный поток) закончит свое выполнение
        }
        log.info("All threads finished.");
        long finishTime = System.currentTimeMillis();
        calendar.setTimeInMillis(finishTime - startTime);
        log.info("RESULT TIME of matrixMultiplyWithThreads: " + calendar.get(Calendar.SECOND) + " SECONDS");
        log.info("#Threads Result \n" + printMatrix(resultMatrix));
        return resultMatrix;

    }

    /**
     * Метод генерации строки для печати матрицы
     * @param int[][] matrix - матрица для печати
     * @return  String - строка для печати матрицы
     **/
    private String printMatrix(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                result.append(" ").append(anInt);
            }
            result.append("\n");
        }
        return result.toString();
    }
}
