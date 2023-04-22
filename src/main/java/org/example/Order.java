package org.example;

import com.sun.org.apache.xpath.internal.operations.Or;

public class Order {
    private String name;
    private int qty;
    public Order(){

    }
    public Order(String name, int qty) {
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return name +" : "+qty;
    }
}
