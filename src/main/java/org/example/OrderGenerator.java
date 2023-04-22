package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrderGenerator {
    public static void generateOrderFiles(int numOfFile) throws  IOException {
        for(int i =0;i<numOfFile;i++){
            List<Order> orderList  = OrderFactory.getOrders(1000);
            OrderFactory.write("./data/order"+i+".txt", orderList);
        }
    }
    public static void generateOrderFiles() throws IOException {
        generateOrderFiles(1000);
    }
}
