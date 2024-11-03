package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.OrderFileDataSource;

import java.io.IOException;


public class PurchaseOrderController {
    @FXML
    private Label nameOfProductLabel;
    @FXML
    private Label unitPriceLabel;
    @FXML
    private Label totalLabel;
    @FXML
    private Label quantityLabel;

    //-------------------------------------------------------------------------------------------------------------
    public Order order;
    public Account loginAccount;

    private AccountFileDataSource dataSource;
    private AccountList accountsList;
    //-------------------------------------------------------------------------------------------------------------------
    private OrderFileDataSource dataSource1;
    private OrderList orderList;

    //----------------------------------------------------------------------------------------------------------------
    @FXML
    public void initialize() {
        //รับ order
        order = (Order) FXRouter.getData();

        dataSource = new AccountFileDataSource();
        dataSource.readData();
        accountsList = dataSource.getAllAccountList();
        this.loginAccount=accountsList.searchUsername(order.getUsername());

        showOrderData();

    }


    @FXML
    void handleBackToSellerButton(ActionEvent event) {
        //search username  return acc --> assign ค่า ให้ loginAccount ส่งกลับไปที่ system user
        try {
            FXRouter.goTo("system_for_user",loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า system_for_user ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void handleCheckoutButton(ActionEvent event) {
        //เขียนไฟล์ csv
        //String username,String storeName,String name,double unitPrice,int amount,double total,String tracking


//        dataSource1 = new OrderFileDataSource();
//        dataSource1.readData();
//        orderList=dataSource1.getAllOrderList();

        DataSource<OrderList> dataSource1 = new OrderFileDataSource();
        OrderList orderList = dataSource1.readData();

        orderList.addOrder(new Order(order.getUsername(),order.getStoreName(),order.getName(),order.getUnitPrice(),order.getAmount(),order.getTotal(),"-"));
        dataSource1.writeData(orderList);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Completed.");

        alert.showAndWait();

        try {
            FXRouter.goTo("shop",loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }
    public void showOrderData(){
        nameOfProductLabel.setText(order.getName());
        unitPriceLabel.setText(order.getUnitPrice1()); //ราคาต่อชิ้น
        order.setTotal(order.calculateTotal(order));
        totalLabel.setText(String.valueOf(order.calculateTotal(order)));//ราคาทั้งหมด
        quantityLabel.setText(String.valueOf(order.getAmount()));
    }




}
