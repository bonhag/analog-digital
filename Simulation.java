import java.util.ArrayList;

public class Simulation {
  // Instance variables to indicate the last round finished by each thread
  private int collectorFinished;
  private int loggerFinished;
  private int statisticianFinished;
  private int reporterFinished;

  // Because our producer and consumer threads never access the buffer at
  // the same time, the producer needs to fill it up completely before the
  // consumer starts consuming.  To simulate the timing more accurately, it
  // makes sense to use a data type that can be written to for the entire
  // CPU burst.
  private ArrayList<Integer> buffer = new ArrayList<Integer>();

  // To hold results passed from Statistician to Reporter
  private byte results;

  // These methods are used like locks -- we can find out which round a
  // thread is working on, and use that to synchronize the threads 
  public synchronized void announceCollectorFinished(int round) {
    collectorFinished = round;
  }

  public synchronized void announceLoggerFinished(int round) {
    loggerFinished = round;
  }

  public synchronized void announceStatisticianFinished(int round) {
    statisticianFinished = round;
  }

  public synchronized void announceReporterFinished(int round) {
    reporterFinished = round;
  }

  // We assume that if a thread has finished round n, then it has finished
  // rounds < n as well
  public synchronized boolean collectorFinished(int round) {
    return collectorFinished >= round;
  }

  public synchronized boolean loggerFinished(int round) {
    return loggerFinished >= round;
  }

  public synchronized boolean statisticianFinished(int round) {
    return statisticianFinished >= round;
  }

  public synchronized boolean reporterFinished(int round) {
    return reporterFinished >= round;
  }

  // The Collector should begin each round with an empty buffer
  public synchronized void clearBuffer() {
    buffer.clear();
  }

  // Method for the Collector to write to the shared buffer
  public synchronized void writeBuffer(int x) {
    buffer.add(x);
  }

  // Method for the Logger and Statistician to read from a shared buffer.
  // The IndexOutOfBoundsException will almost certainly get thrown, because
  // it takes less time for the these processes to read the entire buffer
  // than we allot for their respective CPU bursts.  But, it gives them
  // something to do to use up the rest of that time.
  public synchronized int readBuffer(int i) throws IndexOutOfBoundsException {
    return buffer.get(i);
  }

  // The next two methods are used to pass data from the Statistician to the
  // Reporter.  In an actual application, this might be a more complicated
  // data structure/object.
  public synchronized void setResults(byte r) {
    results = r;
  }

  // Return the results of the statistical calculation to the Reporter
  public synchronized byte getResults() {
    return results;
  }
}

