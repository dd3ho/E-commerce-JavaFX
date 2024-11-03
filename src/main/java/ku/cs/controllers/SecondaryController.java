package ku.cs.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ku.cs.App;


public class SecondaryController {
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    Button secondaryButton;
    private String data;

    @FXML public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                secondaryButton.setText(data);
            }
        });
    }

    public void setData(String data){
        this.data = data;
    }

}