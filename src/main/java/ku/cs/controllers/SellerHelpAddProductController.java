package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class SellerHelpAddProductController {
    @FXML
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            FXRouter.goTo("seller_help");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
