package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.FXRouter;

import java.io.IOException;

public class OurProfileController {


    @FXML
    private ImageView programDevImg1;
    @FXML
    private ImageView programDevImg2;
    @FXML
    private ImageView programDevImg3;

    @FXML
    public void initialize(){
        String path1 = "images/nice.jpg";
        programDevImg1.setImage(new Image("file:" + path1, true));
        String path2 = "images/parn.jpeg";
        programDevImg2.setImage(new Image("file:" + path2, true));
        String path3 = "images/mew.jpg";
        programDevImg3.setImage(new Image("file:" + path3, true));
    }
    @FXML
    public void handleBackButton(ActionEvent event){
        try {
            FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า first_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}