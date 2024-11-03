package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ku.cs.App;
import ku.cs.models.Product;
import ku.cs.services.MyListener;

public class ItemController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent){
        myListener.onClickListener(productTemp);
    }
    private Product productTemp;
    private MyListener myListener;

    public void setData(Product product,MyListener myListener) {
//        System.out.println(product.getImagePath());
        this.productTemp = product;
        this.myListener = myListener;
        nameLabel.setText(product.getName());
        priceLabel.setText(App.CURRENCY + product.getPrice());
        Image image = new Image("file:"+product.getImagePath(), true);
        img.setImage(image);
    }




}
