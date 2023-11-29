import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParallelSumCalculator extends Thread {

    public static void main(String[] args) {
        int numberOfFiles = 997;
        int numbersPerFile = 1000;

        deleteFiles(numberOfFiles);

        // Очистка файлов перед генерацией новых данных
        //clearFiles(numberOfFiles);

        // Генерация файлов с случайными числами
        generateFiles(numberOfFiles, numbersPerFile);

        // Замер времени выполнения для однопоточного режима
        long sequentialStartTime = System.currentTimeMillis();
        int sequentialSum = calculateSequentialSum(numberOfFiles);
        long sequentialEndTime = System.currentTimeMillis();
        long sequentialDuration = sequentialEndTime - sequentialStartTime;

        // Замер времени выполнения для многопоточного режима
        long parallelStartTime = System.currentTimeMillis();
        int parallelSum = calculateParallelSum(numberOfFiles);
        long parallelEndTime = System.currentTimeMillis();
        long parallelDuration = parallelEndTime - parallelStartTime;

        // Вывод результатов и времени выполнения
        System.out.println("Однопоточная сумма чисел из файлов: " + sequentialSum);
        System.out.println("Время выполнения однопоточного режима: " + sequentialDuration + " мс");

        System.out.println("Многопоточная сумма чисел из файлов: " + parallelSum);
        System.out.println("Время выполнения многопоточного режима: " + parallelDuration + " мс");
    }

    private static int calculateSequentialSum(int numberOfFiles) {
        int totalSum = 0;

        for (int i = 1; i <= numberOfFiles; i++) {
            List<Integer> numbers = readFromFile("file" + i + ".txt");
            for (Integer number : numbers) {
                totalSum += number;
            }
        }

        return totalSum;
    }

    private static int calculateParallelSum(int numberOfFiles) {
        List<SumThread> threads = new ArrayList<>();

        for (int i = 1; i <= numberOfFiles; i++) {
            String fileName = "file" + i + ".txt";
            SumThread thread = new SumThread(fileName);
            thread.start();
            threads.add(thread);
        }

        // Ждем завершения всех потоков
        for (SumThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Суммируем результаты из каждого потока
        int totalSum = 0;
        for (SumThread thread : threads) {
            totalSum += thread.getSum();
        }

        return totalSum;
    }


    private static void clearFiles(int numberOfFiles) {
        for (int i = 1; i <= numberOfFiles; i++) {
            clearFile("file" + i + ".txt");
        }
    }

    private static void clearFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private static void generateFiles(int numberOfFiles, int numbersPerFile) {
        Random random = new Random();

        for (int i = 1; i <= numberOfFiles; i++) {
            List<Integer> numbers = new ArrayList<>();
            for (int j = 0; j < numbersPerFile; j++) {
                numbers.add(random.nextInt(21) - 10);
            }
            writeToFile("file" + i + ".txt", numbers);
        }
    }

    private static void writeToFile(String fileName, List<Integer> numbers) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (Integer number : numbers) {
                writer.write(number + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFiles(int numberOfFiles) {
        for (int i = 1; i <= numberOfFiles; i++) {
            deleteFile("file" + i + ".txt");
        }
    }

    private static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Файл " + fileName + " успешно удален");
            } else {
                System.out.println("Не удалось удалить файл " + fileName);
            }
        } else {
            System.out.println("Файл " + fileName + " не существует");
        }
    }

    public static List<Integer> readFromFile(String fileName) {
        List<Integer> numbers = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbers;
    }
}


