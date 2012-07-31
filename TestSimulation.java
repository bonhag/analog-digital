public class TestSimulation implements Runnable {
  public static void main(String[] args) {
    new Thread(new TestSimulation()).start();
  }

  public void run() {
    // Create Simulation thread
    Simulation sim = new Simulation();

    // Create four activity threads and set their priorities appropriately
    Thread collectorThread = new Thread(new Collector(sim));
    collectorThread.setPriority(7);

    Thread loggerThread = new Thread(new Logger(sim));
    loggerThread.setPriority(9);

    Thread statisticianThread = new Thread(new Statistician(sim));
    statisticianThread.setPriority(3);

    Thread reporterThread = new Thread(new Reporter(sim));
    reporterThread.setPriority(5);

    // A day at the races
    collectorThread.start();
    loggerThread.start();
    statisticianThread.start();
    reporterThread.start();
  }
}

