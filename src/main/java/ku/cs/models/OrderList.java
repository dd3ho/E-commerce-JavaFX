package ku.cs.models;

import java.util.ArrayList;

public class OrderList {

    private ArrayList<Order> orders;

    public OrderList(){
        orders = new ArrayList<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }
    

    public ArrayList<Order> getAllOrderList(){
        return orders;
    }


    public String toCsv(){
        String result = "";
        for (Order order : this.orders){
            result += order.toCsv() + "\n";
        }
        return result;
    }




}