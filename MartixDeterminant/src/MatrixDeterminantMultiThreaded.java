import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class MatrixDeterminantMultiThreaded extends MatrixUtils {

    private final int[][] matrix;

    public MatrixDeterminantMultiThreaded(int[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    protected Integer compute() {
        int size = matrix.length;

        if (size == 1) {
            return matrix[0][0];
        } else if (size == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        int determinant = 0;
        MatrixDeterminantMultiThreaded[] tasks = new MatrixDeterminantMultiThreaded[size];

        for (int i = 0; i < size; i++) {
            int[][] minor = getMinor(matrix, 0, i);
            int sign = (int) Math.pow(-1, i);
            tasks[i] = new MatrixDeterminantMultiThreaded(minor);
        }

        invokeAll(tasks);

        for (int i = 0; i < size; i++) {
            int sign = (int) Math.pow(-1, i);
            determinant += sign * matrix[0][i] * tasks[i].join();
        }

        return determinant;
    }

//    private int[][] getMinor(int[][] matrix, int row, int col) {
//        int size = matrix.length - 1;
//        int[][] minor = new int[size][size];
//
//        for (int i = 0, rowDest = 0; i < matrix.length; i++) {
//            if (i != row) {
//                for (int j = 0, colDest = 0; j < matrix[i].length; j++) {
//                    if (j != col) {
//                        minor[rowDest][colDest] = matrix[i][j];
//                        colDest++;
//                    }
//                }
//                rowDest++;
//            }
//        }
//
//        return minor;
//    }

    public static void main(String[] args) {
        int N = 10;
        //int[][] preExistingMatrix = generateMatrix(N);
        int[][] preExistingMatrix = {
                {7, 6, 2, 3, 4, 0, 1, 1, 5, 1, 6, 4},
                {3, 4, 4, 1, 1, 6, 6, 9, 2, 5, 5, 1},
                {5, 7, 0, 9, 5, 3, 3, 7, 9, 2, 5, 4},
                {9, 3, 1, 2, 9, 1, 4, 1, 6, 5, 7, 0},
                {6, 9, 7, 5, 3, 0, 2, 4, 1, 5, 6, 3},
                {0, 9, 6, 2, 6, 3, 5, 5, 3, 2, 7, 9},
                {4, 8, 1, 8, 6, 0, 0, 3, 3, 2, 7, 6},
                {1, 9, 6, 9, 9, 2, 0, 1, 9, 0, 7, 5},
                {4, 7, 4, 0, 5, 5, 7, 5, 4, 9, 4, 5},
                {3, 2, 1, 4, 8, 6, 8, 4, 8, 5, 6, 4},
                {3, 1, 3, 7, 4, 0, 2, 4, 6, 2, 0, 5},
                {9, 4, 4, 5, 3, 3, 4, 3, 3, 6, 8, 2}


        };
        printMatrix(preExistingMatrix);

        ForkJoinPool pool = new ForkJoinPool();
        MatrixDeterminantMultiThreaded determinantTask = new MatrixDeterminantMultiThreaded(preExistingMatrix);
        long startTime = System.currentTimeMillis();
        int result = pool.invoke(determinantTask);
        long endTime = System.currentTimeMillis();

        System.out.println("Определитель матрицы: " + result);
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
    }
}
