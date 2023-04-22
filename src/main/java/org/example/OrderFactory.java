package org.example;


import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderFactory {
    private static Lorem lorem = LoremIpsum.getInstance();
    public  static List<Order> getOrders(int number){
        Random random = new Random();
            List<Order> orders = new ArrayList<>(number);
            for(int i =0;i<number;i++){
                orders.add( new Order(lorem.getCity(),random.nextInt(100)));
            }
            return orders;
    }
    public static void write(String fileName, List<Order> orders) throws IOException {
        StringBuilder sb = new StringBuilder();
        for(Order order:orders){
            sb.append(order.getName()).append(",").append(order.getQty()).append("\n");
        }
        Files.write(Paths.get(fileName),sb.toString().getBytes());
    }
    public static List<Order> fromFile(String fileName) throws IOException {
        List<Order>orders = new ArrayList<>(100);
        List<String>items = Files.readAllLines(Paths.get(fileName));
        for(String line:items){
            String[] arr = line.split(",");
            Order order = new Order( arr[0], Integer.parseInt( arr[1]));
            orders.add(order);
        }
        return orders;
    }
}
