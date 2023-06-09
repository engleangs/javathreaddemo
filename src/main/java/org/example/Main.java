package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    /**
     * Write data to file , this is for generate random data to write line by line seperated by comma ( csv )
     * @throws IOException
     */
    public static void writeData() throws IOException {
        OrderGenerator.generateOrderFiles();
    }

    /**
     * Read order list , from file via file path
     * @throws IOException
     */
    public static void readOrders() throws IOException {
        List<Order> orders = OrderFactory.fromFile("./data/order1.txt");
        for(Order o:orders){
            System.out.println(o);
        }
    }

    /**
     * Experiment saving single instance of order to database
     * @throws SQLException
     */
    public static void saveOrder() throws SQLException {
        MySqlConnection connection = new MySqlConnection("root","root","localhost","8889","thread_demo");
        connection.save( new Order("Testing",1));
    }

    /**
     * Experiment saving batch of orders into databases
     * @throws SQLException
     */
    public static void saveOrders() throws SQLException {
        MySqlConnection connection = new MySqlConnection("root","root","localhost","8889","thread_demo");
        connection.saveBatch(Arrays.asList(new Order("T1",1), new Order("T2",2)));
    }

    /**
     * Test all
     * @throws SQLException
     * @throws IOException
     */
    public static void test() throws SQLException, IOException {
        System.out.println("Hello world!");
        MySqlConnection connection = new MySqlConnection("root","root","localhost","8889","thread_demo");
        connection.save( new Order("Testing",1));
        connection.saveBatch(Arrays.asList(new Order("T1",1), new Order("T2",2)));
        List<Order> orderList = OrderFactory.getOrders(100);
        OrderFactory.write("./data/order1.txt",orderList);
        List<Order> orders = OrderFactory.fromFile("./data/order1.txt");
        for(Order o:orders){
            System.out.println(o);
        }
        connection.saveBatch(orders);
    }

    /**
     * Place holder function to implement for learners
     * @throws IOException
     */
    public static void readAndWriteUsingThread() throws IOException, InterruptedException, SQLException {
        List<List<Order>> orderItems = new ArrayList<>();
        for(int i =0;i<1000;i++){
            String fileName = "./data/order"+i+".txt";
            List<Order> orders = OrderFactory.fromFile(fileName);
            orderItems.add(orders);
        }
        MySqlConnection connection = new MySqlConnection("root","root","localhost","8889","thread_demo");
        int i = 0;
        int count = 0;
        int totalRecord = 0;
        while (i < orderItems.size()){
            List<Order> orders = orderItems.get(i);
            i = i + 3;
            connection.saveBatch(orders);
            count++;
            totalRecord += orders.size();
            System.out.println(" save from "+Thread.currentThread().getName() +" total  list : "+count +" records : "+totalRecord);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                int count = 0;
                int totalRecord = 0;
                while (i < orderItems.size()){
                    List<Order> orders = orderItems.get(i);
                    i = i + 3;
                    totalRecord += orders.size();
                    connection.saveBatch(orders);
                    count++;
                    System.out.println(" save from "+Thread.currentThread().getName() +" total  list : "+count +" records : "+totalRecord);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                int count = 0;
                int totalRecord = 0;
                while (i < orderItems.size()){
                    List<Order> orders = orderItems.get(i);
                    i = i + 3;
                    connection.saveBatch(orders);
                    count++;
                    totalRecord += orders.size();
                    System.out.println(" save from "+Thread.currentThread().getName() +" total  list : "+count +" records : "+totalRecord);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        Thread th3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 2;
                int count = 0;
                int totalRecord = 0;
                while (i < orderItems.size()){
                    List<Order> orders = orderItems.get(i);
                    i = i + 3;
                    connection.saveBatch(orders);
                    count++;
                    totalRecord += orders.size();
                    System.out.println(" save from "+Thread.currentThread().getName() +" total  list : "+count +" records : "+totalRecord);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });
        th1.start();
        th2.start();
        th3.start();
        th1.join();
        th2.join();
        th3.join();
        
    }


    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        //writeData();
        System.out.println("Hello world....");
        System.out.println("Hi");
        System.out.println("Testing");
        readAndWriteUsingThread();


    }
}