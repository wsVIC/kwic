package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Sort {
    public static ArrayList<String> getSortedLines(ArrayList<String> lines) {
        Collections.sort(lines);//排序
        return lines;
    }

    // 测试
    public static void main(String[] args) {

        ArrayList<String> testLines = new ArrayList<>();
        testLines.add("");
        testLines.add("apple");
        testLines.add("zebra and pig");
        testLines.add("orange");
        testLines.add("banana");

        ArrayList<String> sortedLines = getSortedLines(testLines);

        System.out.println("Sorted Lines: " + sortedLines);
    }
}
