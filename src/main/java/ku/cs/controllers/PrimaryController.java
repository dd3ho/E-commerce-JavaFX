package ku.cs.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class PrimaryController {

    @FXML
    private void switchToSecondary(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        SecondaryController sc = loader.getController();
        sc.setData("From primary");

        stage.show();

    }

}

