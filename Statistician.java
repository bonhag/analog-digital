public class Statistician implements Runnable {
  private final int STAT_TIME = 2000;
  private static int round = 1;
  private Simulation sim;

  public Statistician(Simulation s) {
    sim = s;
  }
  
  public void run() {
    while (true) {
      // Wait for Collector thread to finish current round
      while (!sim.collectorFinished(round)) 
        ;

      System.out.println("Statistician is thinking");

      // Simulate one long CPU burst
      long start = System.currentTimeMillis();
      int i = 0;
      byte results = 0;

      while (System.currentTimeMillis() <= start + STAT_TIME) {
        try {
          // Some fake analysis
          results = (byte)(sim.readBuffer(i++) + results);
        } catch (IndexOutOfBoundsException e) {
          results = (byte)(results * results);
        }
      }

      // Pass results to Simulator  
      sim.setResults(results);

      // Let everybody know Statistician has finished this round
      System.out.println("Statistician is finished with round " + round);
      sim.announceStatisticianFinished(round);
      round++;
    }
  }
}

