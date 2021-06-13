package ru.cft.focusstart.task2.cmdline;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.cft.focusstart.task2.inputoutput.OutputStrategy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CmdParserTest {
    CmdLineParser sut;
    String[] fParam = {"-f"};
    String[] cParam = {"-c"};
    String[] noParams = {};
    String[] incorrectParams = {"-b","=q","-z"};

    @BeforeEach
    public void init() {
        sut = new CmdLineParser();
    }

    @Test
    void parseFileParam() {
        sut.parseCmdLine(fParam);
        Assertions.assertEquals(OutputStrategy.TO_FILE, sut.getOutputStrategy());
    }

    @Test
    void parseConsoleParam() {
        sut.parseCmdLine(cParam);
        Assertions.assertEquals(OutputStrategy.TO_CONSOLE, sut.getOutputStrategy());
    }

    @Test
    void parseIncorrectParam() {
        sut.parseCmdLine(incorrectParams);
        Assertions.assertEquals(OutputStrategy.TO_CONSOLE, sut.getOutputStrategy());
    }

    @Test
    void parseNoParam() {
        sut.parseCmdLine(noParams);
        Assertions.assertEquals(OutputStrategy.TO_CONSOLE, sut.getOutputStrategy());
    }
}
