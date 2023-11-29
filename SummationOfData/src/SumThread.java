import java.util.List;

public class SumThread extends ParallelSumCalculator{
    private String fileName;
    private int sum;

    public SumThread(String fileName) {
        this.fileName = fileName;
        this.sum = 0;
    }

    @Override
    public void run() {
        List<Integer> numbers = readFromFile(fileName);

        for (Integer number : numbers) {
            sum += number;
        }
    }

    public int getSum() {
        return sum;
    }
}
