package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class SellerHelpController {
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
    public void handleChangePasswordHelpButton( ) {
        try {
            FXRouter.goTo("seller_help_change_password");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller help change password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleAddProductHelpButton( ) {
        try {
            FXRouter.goTo("seller_help_add_product");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller help add product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleEditProductHelpButton( ) {
        try {
            FXRouter.goTo("seller_help_edit_product");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller help edit product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleListOfProductsHelpButton( ) {
        try {
            FXRouter.goTo("seller_help_list_of_products");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller help list of products ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
