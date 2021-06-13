package ru.cft.focusstart.task4;

import com.google.common.collect.Lists;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class.getName());
    private static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
    private static final int THRESHOLD = 1000000;

    public static void main(String[] args) {
        logger.info("current available processors {}", AVAILABLE_PROCESSORS);
        List<BigInteger> numbersToCompute = getNumbersToComputeFromInput();
        List<List<BigInteger>> subLists = divideCollectionByAvailableProcessors(numbersToCompute);
        logger.info("Initial collection of input numbers has been divided into {} subCollections", subLists.size());

        Runnable parallel = () -> {
            ExecutorService executorService = Executors.newFixedThreadPool(AVAILABLE_PROCESSORS);
            List<Future<BigInteger>> subResults = new ArrayList<>();
            BigInteger result = BigInteger.ZERO;
            subLists.stream().parallel().forEach(subList -> subResults.add(executorService.submit(new SimpleTask(subList))));
            for (Future<BigInteger> subResult : subResults) {
                try {
                    result = result.add(subResult.get());
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Process of getting result was interrupted. Possible cause - {}", e.getCause().getMessage());
                    Thread.currentThread().interrupt();
                }
            }
            executorService.shutdown();
            logger.info("Parallel result is: {}", result);
        };

        Runnable sequential = () -> {
            BigInteger acc = FunctionUtil.sequentialPow(numbersToCompute);
            logger.info("Sequential Result is: {}", acc);
        };


        sequential.run();
        parallel.run();

        logger.info("Testing computation time after some optimization");
        benchmark(sequential);
        benchmark(parallel);

        logger.info("Testing computation time after some optimization");
        benchmark(sequential);
        benchmark(parallel);


    }

    @NotNull
    private static List<List<BigInteger>> divideCollectionByAvailableProcessors(List<BigInteger> numbersToCompute) {
        return Lists.partition(numbersToCompute, numbersToCompute.size() > THRESHOLD ?
                numbersToCompute.size() / AVAILABLE_PROCESSORS :
                numbersToCompute.size());
    }

    private static List<BigInteger> getNumbersToComputeFromInput() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<BigInteger> numbersToCompute = IntStream.range(0, n)
                .mapToObj(BigInteger::valueOf)
                .collect(Collectors.toList());
        logger.info("Collection of input numbers received. Size - {}", numbersToCompute.size());
        return numbersToCompute;
    }

    static void benchmark(Runnable runnable) {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            logger.warn("Sleeping interrupted. Possible cause - {}", e.getCause().getMessage());
            Thread.currentThread().interrupt();
        }
        long before = System.currentTimeMillis();
        runnable.run();
        long after = System.currentTimeMillis();
        logger.info("Executed in: {} {}", (after - before), System.lineSeparator());
    }
}
