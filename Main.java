public class Main {
    private static final int INCREMENTS_PER_THREAD = 1000000;
    private static final int NUMBER_OF_RUNS = 5;
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        int[] threadCounts = {2, 4, 8, 16, 32};

        System.out.println("===== TAS LOCK EXPERIMENT =====");
        for (int i = 0; i < threadCounts.length; i++) {
            runTASExperiment(threadCounts[i]);
        }

        System.out.println();
        System.out.println("===== TTAS LOCK EXPERIMENT =====");
        for (int i = 0; i < threadCounts.length; i++) {
            runTTASExperiment(threadCounts[i]);
        }
    }

    public static void runTASExperiment(int numberOfThreads) throws InterruptedException {
        long totalTime = 0;
        long totalTestAndSetCalls = 0;

        System.out.println();
        System.out.println("TAS - " + numberOfThreads + " threads");

        for (int run = 1; run <= NUMBER_OF_RUNS; run++) {
            counter = 0;
            TASLock lock = new TASLock();
            Thread[] threads = new Thread[numberOfThreads];

            long startTime = System.nanoTime();

            for (int i = 0; i < numberOfThreads; i++) {
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                        lock.lock();
                        counter++;
                        lock.unlock();
                    }
                });
                threads[i].start();
            }

            for (int i = 0; i < numberOfThreads; i++) {
                threads[i].join();
            }

            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1000000;
            int testAndSetCalls = lock.getTestAndSetCount();

            totalTime += executionTime;
            totalTestAndSetCalls += testAndSetCalls;

            System.out.println("Run " + run + ": " + executionTime + " ms, " + testAndSetCalls + " testAndSet calls");
        }

        System.out.println("Average execution time: " + (totalTime / (double) NUMBER_OF_RUNS) + " ms");
        System.out.println("Average testAndSet calls: " + (totalTestAndSetCalls / (double) NUMBER_OF_RUNS));
    }

    public static void runTTASExperiment(int numberOfThreads) throws InterruptedException {
        long totalTime = 0;
        long totalTestAndSetCalls = 0;

        System.out.println();
        System.out.println("TTAS - " + numberOfThreads + " threads");

        for (int run = 1; run <= NUMBER_OF_RUNS; run++) {
            counter = 0;
            TTASLock lock = new TTASLock();
            Thread[] threads = new Thread[numberOfThreads];

            long startTime = System.nanoTime();

            for (int i = 0; i < numberOfThreads; i++) {
                threads[i] = new Thread(() -> {
                    for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                        lock.lock();
                        counter++;
                        lock.unlock();
                    }
                });
                threads[i].start();
            }

            for (int i = 0; i < numberOfThreads; i++) {
                threads[i].join();
            }

            long endTime = System.nanoTime();
            long executionTime = (endTime - startTime) / 1000000;
            int testAndSetCalls = lock.getTestAndSetCount();

            totalTime += executionTime;
            totalTestAndSetCalls += testAndSetCalls;

            System.out.println("Run " + run + ": " + executionTime + " ms, " + testAndSetCalls + " testAndSet calls");
        }

        System.out.println("Average execution time: " + (totalTime / (double) NUMBER_OF_RUNS) + " ms");
        System.out.println("Average testAndSet calls: " + (totalTestAndSetCalls / (double) NUMBER_OF_RUNS));
    }
}