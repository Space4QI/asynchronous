import java.util.Random;
import java.util.concurrent.Semaphore;

class Servant implements Runnable {
    private final Semaphore table;
    private final Semaphore tobaccoAndPaperSemaphore;
    private final Semaphore paperAndMatchesSemaphore;
    private final Semaphore matchesAndTobaccoSemaphore;
    private final Window window;
    private Random random = new Random();

    public Servant(Semaphore table, Semaphore tobaccoAndPaperSemaphore, Semaphore paperAndMatchesSemaphore, Semaphore matchesAndTobaccoSemaphore, Window window) {
        this.table = table;
        this.tobaccoAndPaperSemaphore = tobaccoAndPaperSemaphore;
        this.paperAndMatchesSemaphore = paperAndMatchesSemaphore;
        this.matchesAndTobaccoSemaphore = matchesAndTobaccoSemaphore;
        this.window = window;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Размещаем случайные ингредиенты на столе
                int randomIngredients = random.nextInt(3);
                if (randomIngredients == 0) { // matches
                    window.log("Servant placed Tobacco and Paper on the table.");
                    tobaccoAndPaperSemaphore.release();
                } else if (randomIngredients == 1) { //tobacco
                    window.log("Servant placed Paper and Matches on the table.");
                    paperAndMatchesSemaphore.release();
                } else { //paper
                    window.log("Servant placed Matches and Tobacco on the table.");
                    matchesAndTobaccoSemaphore.release();
                }
                table.acquire(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}