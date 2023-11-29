import java.util.concurrent.Semaphore;


public class Main {
    public static void main(String[] args) {
        Semaphore table = new Semaphore(0);
        Semaphore tobaccoAndPaperSemaphore = new Semaphore(0);
        Semaphore paperAndMatchesSemaphore = new Semaphore(0);
        Semaphore matchesAndTobaccoSemaphore = new Semaphore(0);

        Window window = new Window();
        Panel[] panels = window.getPanels();

        Smoker smoker1 = new Smoker("Smoker 1 (Tobacco)", table, paperAndMatchesSemaphore, panels[0]);
        Smoker smoker2 = new Smoker("Smoker 2 (Paper)", table, matchesAndTobaccoSemaphore, panels[1]);
        Smoker smoker3 = new Smoker("Smoker 3 (Matches)", table, tobaccoAndPaperSemaphore, panels[2]);
        Servant servant = new Servant(table, tobaccoAndPaperSemaphore, paperAndMatchesSemaphore, matchesAndTobaccoSemaphore, window);

        Thread t1 = new Thread(smoker1);
        Thread t2 = new Thread(smoker2);
        Thread t3 = new Thread(smoker3);
        Thread servantThread = new Thread(servant);

        servantThread.start();
        t1.start();
        t2.start();
        t3.start();
    }
}
