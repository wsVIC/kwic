package org.example;

import java.nio.file.*;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Output {

    // 接口：将 content 存储到指定的文件地址
    public void save(ArrayList<String> content, String saveLoc) {
        try {
            // 检查文件是否已存在
            Path filePath = Paths.get(saveLoc);
            if (Files.exists(filePath)) {
                System.out.println("Error: File already exists at " + saveLoc);
                return;
            }

            // 将 content 写入文件
            Files.write(filePath, content, StandardCharsets.UTF_8);
            System.out.println("Content successfully saved to " + saveLoc);
        } catch (IOException e) {
            System.out.println("Error: Unable to save content to " + saveLoc);
            e.printStackTrace();
        }
    }

    // 测试函数
    public static void main(String[] args) {
        Output output = new Output();

        // 示例用法
        ArrayList<String> contentToSave = new ArrayList<>();
        contentToSave.add("This is a test.");

        // 指定保存地址
        String saveLocation = "output.txt";

        // 调用保存接口
        output.save(contentToSave, saveLocation);
    }
}
