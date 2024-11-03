package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class HelpController {

    @FXML
    public void handleUserHelpButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_help");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleSellerHelpButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("seller_help");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleBackButton(ActionEvent event){
        try {
            FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า first page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
