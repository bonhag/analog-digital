import java.io.*;

public class Logger implements Runnable {
  private final int LOG_TIME = 500;
  private static int round = 1;
  private Simulation sim;
  private DataOutputStream outFile = null;

  public Logger(Simulation s) {
    sim = s;
    try {
      // Open logfile
      outFile = new DataOutputStream(new FileOutputStream("log.bin"));
    } catch (FileNotFoundException e) {
      System.out.println("Log file couldn't be opened");
      System.exit(1);
    }
  }

  public void run() {
    while (true) {
      // Wait for Collector to finish current round
      while (!sim.collectorFinished(round))
        ;

      // Logger has two bursts of CPU time
      for (int w = 0; w < 2; w++) {
        System.out.println("Logger is writing to disk");

        // Simulate CPU burst
        long start = System.currentTimeMillis();
        int i = 0;
        while (System.currentTimeMillis() <= start + LOG_TIME) {
          try {
            // Read from buffer and write to output file
            int data = sim.readBuffer(i++);
            outFile.writeInt(data);
          } catch (IndexOutOfBoundsException e) {
            // We've run out of data to process, so bleed out the clock
          } catch (IOException e) {
            System.out.println("Error writing to log file");
          }
        }

        if (w == 1) {
          // Let everybody know Logger has finished
          System.out.println("Logger is finished with round " + round);
          sim.announceLoggerFinished(round);
          round++;
        }

        // Simulate sleeping
        System.out.println("Logger is sleeping");
        try {
          Thread.sleep(1500);
        } catch (InterruptedException ie) {
        }
      }
    }
  }
}

