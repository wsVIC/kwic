package org.example;

import java.util.ArrayList;

public class Shifter {
    
    // 接口1：对整个文档进行循环移位
    public ArrayList<String> getShiftedLines(ArrayList<String> inputLines) {
        ArrayList<String> shiftedDocument = new ArrayList<>();
        
        for (String line : inputLines) {
            // 调用接口2完成对每一行的循环移位
            ArrayList<String> shiftedLine = getShiftedLine(line);
            shiftedDocument.addAll(shiftedLine);
        }
        
        return shiftedDocument;
    }

    // 接口2：对每一行进行循环移位
    public ArrayList<String> getShiftedLine(String line) {
        ArrayList<String> shiftedLine = new ArrayList<>();
        
        if (line == null || line.trim().isEmpty()) {
            // 错误处理：空行
            System.out.println("Error: Empty line!");
            return shiftedLine;
        }

        // 将每一行按字（单词）拆分
        String[] words = line.split("\\s+");

        // 对每一行以字为单位进行循环移位
        for (int i = 0; i < words.length; i++) {
            StringBuilder shiftedString = new StringBuilder();
            
            // 构建循环移位后的字符串
            for (int j = 0; j < words.length; j++) {
                int index = (i + j) % words.length;
                shiftedString.append(words[index]).append(" ");
            }
            
            // 移除末尾的空格并添加到结果列表
            shiftedLine.add(shiftedString.toString().trim());
        }

        return shiftedLine;
    }

    public static void main(String[] args) {
        Shifter shifter = new Shifter();

        // 示例用法
        ArrayList<String> inputLines = new ArrayList<>();
        inputLines.add("This is a test.");
        
        ArrayList<String> shiftedDocument = shifter.getShiftedLines(inputLines);

        // 打印结果
        for (String shiftedLine : shiftedDocument) {
            System.out.println(shiftedLine);
        }
    }
}
