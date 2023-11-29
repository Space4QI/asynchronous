import java.util.concurrent.Semaphore;

class Smoker implements Runnable {
    private final String name;
    private final Semaphore table;
    private final Panel panel;
    private final Semaphore semaphore;

    public Smoker(String name, Semaphore table, Semaphore semaphore, Panel panel) {
        this.name = name;
        this.table = table;
        this.semaphore = semaphore;
        this.panel = panel;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                semaphore.acquire();
                panel.changeColor(true);
                System.out.println(name + " is making a cigarette.");
                table.release();
                Thread.sleep(2000);
                System.out.println(name + " is smoking.");
                Thread.sleep(2000);
                panel.changeColor(false);
                System.out.println(name + " finished smoking.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
