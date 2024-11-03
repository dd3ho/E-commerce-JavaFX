package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.FXRouter;

import java.io.IOException;

public class FirstPageController {

    @FXML
    private ImageView first_page;

    String path = getClass().getResource("/Image/first_page.png").toExternalForm();

    @FXML
    public void initialize() {
        first_page.setImage(new Image(path));
    }

    @FXML
    public void handleHelpButton(ActionEvent actionEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ help
            FXRouter.goTo("help");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า help ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleOurProfileButton(ActionEvent actionEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ our_profile
            FXRouter.goTo("our_profile");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า our profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) {
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ login
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}


