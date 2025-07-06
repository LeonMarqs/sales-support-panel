package br.com.sales.support.panel.ssp.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UseCaseTest {
    @Test
    void testExecute() {
        UseCase<String, String> useCase = new UseCase<>() {
            @Override
            public String execute(String input) {
                return "Executed with input: " + input;
            }
        };

        String result = useCase.execute("Test Input");
        assertEquals("Executed with input: Test Input", result);
    }
}
