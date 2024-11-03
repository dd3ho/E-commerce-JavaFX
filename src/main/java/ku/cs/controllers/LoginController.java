package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.FXRouter;
import ku.cs.models.Account;
import ku.cs.models.AccountList;
import ku.cs.models.SellerAccount;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;


import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordPasswordField;

    //เป็น account ที่ไว้ใช้สำหรับ login
    public Account loginAccount;

    private AccountList accountList;
    private AccountFileDataSource dataSource;


    @FXML public void handleLoginButton(ActionEvent event) {
        // รับข้อมูลจาก TextField ข้อมูลที่รับเป็น String เสมอ
        String usernameStr = usernameTextField.getText();
        String passwordStr = passwordPasswordField.getText();

//        DataSource <AccountList> dataSource = new AccountFileDataSource();
//        AccountList accounts = dataSource.readData();
        dataSource = new AccountFileDataSource();
        dataSource.readData();
        accountList = dataSource.getAllAccountList();

        // --> return ข้อมูล account ของคนที่ username นี้
        loginAccount = accountList.searchUsername(usernameStr);

        // หา login account ไม่เจอ
        if (loginAccount == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Please check your information and try again.");

            alert.showAndWait();
        }


        if ((loginAccount.isBanned())) {

//            loginAccount.setCountLogin();
//            System.out.println(loginAccount.getCountLogin());

            Account account = accountList.searchUsername(loginAccount.getUsername());
            //เพิ่มจำนวน การพยายามเข้าใช้งาน ของ user ที่ถูกแบน
            account.setCountLogin();
//            System.out.println(account.getCountLogin());
            dataSource.writeData(accountList);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("This account was banned.");

            alert.showAndWait();

        }else{
            //check password ของ loginAccount ว่า ตรงกับที่ user กรอกเข้ามา
            if( !(loginAccount.isPassword(passwordStr))){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("Please check your information and try again.");

                alert.showAndWait();



            }else{
                loginAccount.setLastLogin();
                Account account = accountList.searchUsername(loginAccount.getUsername());
                account.setLastLogin();
                dataSource.writeData(accountList);


                //System.out.println(loginAccount.getLastLogin());

                if(loginAccount.getType().equals("Seller")){
                    try {
                        FXRouter.goTo("system_for_seller", (SellerAccount)loginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า System for seller ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }

                else if(loginAccount.getType().equals("User")){
                    try {
                        FXRouter.goTo("system_for_user",loginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า System for user ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }

                else if(loginAccount.getType().equals("Admin")){
                    try {
                        FXRouter.goTo("system_for_admin",loginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า System for admin ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }
            }

        }


        // clear ช่อง TextField
        usernameTextField.clear();
        passwordPasswordField.clear();


    }




//        for (Account account: accounts.getAllAccounts()){
//            if(account.getUsername().equals(usernameStr)&&account.getPassword().equals(passwordStr) && account.getStatus().equals("Not Banned")){
////                System.out.println(account); ---> debug
//
//                // เพิ่มโค้ดที่ เก็บ accountlogin -->เอาไป show label หน้า system
////                accountFileDataSource.getAllAccountList().setLoginAccount(new Account(username,password));
//
//
//                try {
//                    FXRouter.goTo("system_for_user");
//                } catch (IOException e) {
//                    System.err.println("ไปที่หน้า system_for_user ไม่ได้");
//                    System.err.println("ให้ตรวจสอบการกำหนด route");
//                }
//            }else{
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Error!!");
//                alert.setHeaderText(null);
//                alert.setContentText("Please check your information and try again.");
//
//                alert.showAndWait();
//            }
//
//        }



    @FXML
    public void handleSignUpButton(ActionEvent event){
        try {
            FXRouter.goTo("signup");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า signup ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }

    @FXML public void handleBackButton(ActionEvent event){
        try {
            FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า first page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}