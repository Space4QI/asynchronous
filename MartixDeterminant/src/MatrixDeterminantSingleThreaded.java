import java.util.concurrent.ForkJoinPool;

public class MatrixDeterminantSingleThreaded {

    private final int[][] matrix;

    public MatrixDeterminantSingleThreaded(int[][] matrix) {
        this.matrix = matrix;
    }

    public int compute() {
        int size = matrix.length;
        if (size == 1) {
            return matrix[0][0];
        } else if (size == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            int determinant = 0;
            for (int i = 0; i < size; i++) {
                int[][] minor = MatrixUtils.getMinor(matrix, 0, i);
                int sign = (int) Math.pow(-1, i);
                int minorDeterminant = new MatrixDeterminantSingleThreaded(minor).compute();
                determinant += sign * matrix[0][i] * minorDeterminant;
            }
            return determinant;
        }
    }

    public static void main(String[] args) {
        int N = 7;
        //int[][] matrix = MatrixUtils.generateMatrix(N);
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

        MatrixUtils.printMatrix(preExistingMatrix);

        long singleThreadStartTime = System.currentTimeMillis();
        int determinantSingleThread = new MatrixDeterminantSingleThreaded(preExistingMatrix).compute();
        long singleThreadEndTime = System.currentTimeMillis();
        System.out.println("Определитель матрицы (однопоточно): " + determinantSingleThread);
        System.out.println("Время выполнения (однопоточно): " + (singleThreadEndTime - singleThreadStartTime) + " мс");
    }
}

