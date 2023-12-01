package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeoutException;

public class Input {
    private String[] args;
    private ArrayList<String> inputLines;
    private String outFilePath;

    public Input(String[] args) {
        this.args = args;
        this.outFilePath = "out.txt";
        this.inputLines = new ArrayList<>();
    }

    public void parseInput() throws Exception {
        if (this.args.length < 1) {
            throw new Exception("no arg");
        }

        int i = 0;
        while (i < this.args.length) {
            String para = this.args[i];
            switch (para) {
                case "-f"://file
                    i++;
                    this.getInputFromFile(this.args[i]);
                    break;
                case "-s"://network 先启动发送端
                    this.getInputFromNet();
                    break;
                case "-i"://user terminal  输入exit作为结束
                    this.getInputFromTerm();
                    break;
                case "-p"://pipe
                    this.getInputFromPipe();
                    break;
                case "-d"://database url,用户名，密码，数据库名，表名，列名都已在函数中给出，访问数据库还需要在pom中配置驱动
                    this.getInputFromDB();
                    break;
                case "-m"://middleware
                    this.getInputFromMidWare();
                    break;
                case "-o":
                    i++;
                    this.outFilePath = this.args[i];
                    break;
                default:
                    throw new Exception("wrong parameter");
            }
            i++;
        }

    }

    public void getInputFromFile(String path) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            String line;
            while ((line = reader.readLine()) != null) {
                this.inputLines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("input file not found");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
    }

    public void getInputFromNet() {
        try {
            Socket socket = new Socket("localhost", 8000);
            System.out.println("connected to server");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                this.inputLines.add(line);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("net read error");
        }
    }

    public void getInputFromTerm() {
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String line = scan.nextLine();
            inputLines.add(line);
        }
        scan.close();
    }

    public void getInputFromPipe() {
        Scanner scan = new Scanner(System.in);
        String line;
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            this.inputLines.add(line);
        }
        scan.close();
    }

    public void getInputFromDB() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/db1";
            String user = "root";
            String password = "wsdd201512";

            conn = DriverManager.getConnection(url, user, password);

            stmt = conn.createStatement();

            String sql = "select text from table1";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String text = rs.getString("text");
                this.inputLines.add(text);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接和资源
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void getInputFromMidWare() throws IOException, TimeoutException {
        final String QUEUE_NAME = "text";
        ConnectionFactory factory;
        Channel channel;
        com.rabbitmq.client.Connection connection;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        GetResponse response;
        long deliveryTag;
        do {
            response = channel.basicGet(QUEUE_NAME, false);
            if(response == null){
                channel.close();
                connection.close();
            }
            else{
                inputLines.add(new String(response.getBody(), StandardCharsets.UTF_8));
                deliveryTag  = response.getEnvelope().getDeliveryTag();
                channel.basicAck(deliveryTag, false);
            }
        } while (response != null);
    }


    public ArrayList<String> getInputLines() {
        return this.inputLines;
    }

    public String getSaveLocation() {
        return this.outFilePath;
    }

    public static void main(String[] args) {

        Input input = new Input(args);
        try {
            input.parseInput();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(input.getSaveLocation());
        System.out.println(input.getInputLines());
    }
}
