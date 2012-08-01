analog-digital
==============

This is a threaded analog-digital converter simulator I wrote for a class in programming Java threads.  It consists of four threads:

- The `Collector` thread gathers data.  This might be like a heart monitor hooked up to a patient in a hospital.
- The `Logger` thread saves the data gathered by the `Collector` thread.
- The `Statistian` thread performs analysis on the data gathered by the `Collector` thread.
- The `Reporter` thread takes the results of the analysis performed by the `Statistician` thread and formats it for printing.

There is also a `Simulation` class that allows the four threads to share data, and a `TestSimulation` class that creates and runs the four threads.

