package org.example;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Input input = new Input(args);
        input.parseInput();
        Shifter shifter = new Shifter();
        Output output = new Output();
        ArrayList<String> outputLines =
                Sort.getSortedLines(shifter.getShiftedLines(input.getInputLines()));
        output.save(outputLines, input.getSaveLocation());
    }
}
