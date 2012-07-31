public class Reporter implements Runnable {
  private final int REPORT_TIME = 250;
  private static int round = 1;
  private Simulation sim;

  public Reporter(Simulation s) {
    sim = s;
  }

  public void run() {
    while (true) {
      // Wait for Statistician to finish current round
      while (!sim.statisticianFinished(round))
        ;

      // Reporter has two bursts of CPU time
      for (int w = 0; w < 2; w++) {
        System.out.println("Reporter is writing to printer");

        // Simulate CPU burst
        long start = System.currentTimeMillis();
        byte results = sim.getResults();

        while (System.currentTimeMillis() <= start + REPORT_TIME) {
            // Uncomment to get some wacky results
          //System.out.printf("**\n\t%2d\t\n*\t_==\n-* *\n~~~\n", results);
        }

        if (w == 1) {
          // Let everybody know Reporter is finished
          System.out.println("Reporter is finished with round " + round);
          sim.announceReporterFinished(round);
          round++;
        }

        // Simulate sleeping
        System.out.println("Reporter is sleeping");
        try {
          Thread.sleep(750);
        } catch (InterruptedException ie) {
        }
      }
    }
  }
}

