package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ku.cs.FXRouter;
import ku.cs.models.Account;
import ku.cs.models.AccountList;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CreateStoreForUserController {
//    private Account loginAccount= (Account) FXRouter.getData();
    private AccountFileDataSource dataSource;
    private AccountList accountsList ;
    private Account loginAccount;
    private AccountList sellerAccounts;

//    Collection<String> accounts = new ArrayList<>();

    @FXML TextField storeNameTextField;

    @FXML public void initialize(){
        loginAccount = (Account) FXRouter.getData();
    }


    //create store แล้วไปหน้า System for seller
    @FXML public void handleEnterCreateStoreButton(ActionEvent event){

        //อ่านไฟล์
        dataSource = new AccountFileDataSource();
        accountsList = dataSource.readData();

        String storeNameStr = storeNameTextField.getText();

        //เขียน
//        System.out.println(accountsList.toCsv());
//        ห้ามชื่อร้านซ้ำ
        if(accountsList.checkStoreName(storeNameStr)){
            //ไม่ให้สมัคร
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("ไม่ให้สมัคร");

            alert.showAndWait();

            try {
                FXRouter.goTo("system_for_user",loginAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า System for seller ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }

        }else{
            //เรียก changeType ใน AccountList เพื่อเปลี่ยนประเภท จาก user เป็น seller
            accountsList.changeType(loginAccount.getUsername(),storeNameStr); //หาว่าชี้ตัวไหนที่อยู่ใน list
            Account account = accountsList.searchUsername(loginAccount.getUsername());
            account.setStoreName(storeNameStr);
            // เขียนทั้ง list ที่แก้แล้วลงไปในไฟล์ Accounts.csv เลย
            dataSource.writeData(accountsList);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Created Store Already Please Login again.");

            alert.showAndWait();

            try {
                FXRouter.goTo("login");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า System for seller ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    //กลับไปหน้า User
    @FXML public void handleBackGoToUserButton(ActionEvent event){
        try {
            FXRouter.goTo("system_for_user");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า System for user ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}