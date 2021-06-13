package ru.cft.focusstart.task4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Callable;

public class SimpleTask implements Callable<BigInteger> {
    private final List<BigInteger> inputNumbers;
    private BigInteger result = BigInteger.ZERO;
    private static final Logger logger = LoggerFactory.getLogger(SimpleTask.class.getName());

    public SimpleTask(List<BigInteger> inputNumbers) {
        logger.info("worker created with {} numbers to compute", inputNumbers.size());
        this.inputNumbers = inputNumbers;
    }

    @Override
    public BigInteger call() {
        result = FunctionUtil.sequentialPow(inputNumbers);
        logger.info("Computation done, result is {}", result);
        return result;
    }

    public BigInteger getResult() {
        return result;
    }
}
