package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class UserHelpController {
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("help");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleChangePasswordHelpButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_help_change_password");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user help change password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleCreateStoreHelpButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_help_create_store");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user help createstore ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handlePurchaseOrderHelpButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_help_purchase_order");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user help purchase order ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleShopHelpButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("user_help_shop");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า user help shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
