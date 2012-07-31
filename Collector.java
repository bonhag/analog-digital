import java.util.Random;

public class Collector implements Runnable {
  private final int COLLECT_TIME = 250;
  private static int round = 1;
  private Simulation sim;
  private Random r = new Random();

  public Collector(Simulation s) {
    sim = s;
  }

  public void run() {
    while (true) {
      // Wait for Logger and Statistician to finish previous round
      while (!sim.loggerFinished(round - 1) &&
             !sim.statisticianFinished(round - 1))
        ;

      // Clear the buffer
      sim.clearBuffer();

      // Collector has three bursts of CPU time
      for (int w = 0; w < 3; w++) {
        System.out.println("Collector is collecting");

        // Simulate CPU burst
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() <= start + COLLECT_TIME)
          // Read from the ADC and write to a buffer
          sim.writeBuffer(r.nextInt(128)); 

        if (w == 2) {
          // Let everybody know collector is finished
          System.out.println("Collector is finished with round " + round);
          sim.announceCollectorFinished(round);
          round++;
        }

        // Simulate sleeping
        System.out.println("Collector is sleeping");
        try {
          Thread.sleep(750);
        } catch (InterruptedException ie) {
        }
      }
    }
  }
}

