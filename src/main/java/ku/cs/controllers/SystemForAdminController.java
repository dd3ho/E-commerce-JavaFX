package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;
import ku.cs.models.Account;

import java.io.IOException;

public class SystemForAdminController {
    private Account loginAccount;

    @FXML
    public void initialize(){
        loginAccount = (Account) FXRouter.getData();
    }

    @FXML
    public void handleChangeAdminPasswordButton(ActionEvent actionEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ change_password
            FXRouter.goTo("change_password",loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า change_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void handleAccountListButton(ActionEvent actionEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ list_of_account
            FXRouter.goTo("list_of_account");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า list_of_account ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleTableviewButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("table_view_of_account",loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า table_view_of_account");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void handleManageUserReportButton(ActionEvent actionEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ manage_user_report
            FXRouter.goTo("manage_user_report",loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า manage_user_report ไม่ได้");
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
