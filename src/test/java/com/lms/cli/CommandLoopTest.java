package com.lms.cli;

import com.lms.domain.exception.LibraryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommandLoopTest {

    private CommandRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new CommandRegistry();
        registry.register("ECHO", args -> List.of(String.join(",", args)));
        registry.register("TWO", args -> List.of("first", "second"));
        registry.register("FAIL", args -> {
            throw new LibraryException("boom") { };
        });
    }

    private List<String> run(String input) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        new CommandLoop(registry).run(new BufferedReader(new StringReader(input)), out);
        return bytes.toString(StandardCharsets.UTF_8).lines().toList();
    }

    @Test
    void splitsFieldsOnPipeAndKeepsSpaces() throws IOException {
        assertThat(run("ECHO|Ananya Rao|STUDENT\n")).containsExactly("Ananya Rao,STUDENT");
    }

    @Test
    void printsEveryLineACommandReturns() throws IOException {
        assertThat(run("TWO\n")).containsExactly("first", "second");
    }

    @Test
    void skipsBlankLinesAndWindowsLineEndings() throws IOException {
        assertThat(run("ECHO|a\r\n\n   \nECHO|b\n")).containsExactly("a", "b");
    }

    @Test
    void printsErrorAndContinuesAfterLibraryException() throws IOException {
        assertThat(run("FAIL\nECHO|after\n")).containsExactly("ERROR boom", "after");
    }

    @Test
    void reportsAnUnknownCommand() throws IOException {
        assertThat(run("NOPE|x\n")).containsExactly("ERROR unknown command NOPE");
    }
}