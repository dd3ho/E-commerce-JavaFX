package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationAddProductController {

    @FXML private ImageView productImageView;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label qtLabel;
    @FXML private Label pdLabel;
    @FXML private Label statusQtLabel;


    public Product productConfirm;

    public SellerAccount loginAccount;


    @FXML
    public void initialize() {
        productConfirm = (Product) FXRouter.getData();
        showData();
    }

    public void showData(){

        productImageView.setImage(new Image("file:"+productConfirm.getImagePath(), true));
        nameLabel.setText(productConfirm.getName());
        priceLabel.setText(productConfirm.getPrice1());
        qtLabel.setText(productConfirm.getQuantity1());
        pdLabel.setText(productConfirm.getProductDetails());
        statusQtLabel.setText(String.valueOf(productConfirm.getStatus()));
    }

    @FXML
    void handleBackToAddPdButton(ActionEvent event) {
        try {
            FXRouter.goTo("add_product");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add_product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    void handleEnterConfirmProduct(ActionEvent event) {
        //เขียนอ่านไฟล์
        DataSource<ProductList> dataSource = new ProductFileDataSource();
        ProductList products = dataSource.readData();

        products.addProduct(productConfirm);

        //-------------------------------------------------------------------------------------------------------------------------
        Product product = products.searchStoreNameAndProduct(productConfirm.getStoreName(),productConfirm.getName());
        product.setLastUpdateTime();
        dataSource.writeData(products); //เขียนไฟล์ใหม่

        //---------------------------------------------------------------
        //หา account
        DataSource<AccountList> dataSource1 = new AccountFileDataSource();
        AccountList accounts  = dataSource1.readData();

        SellerAccount acc = (SellerAccount) accounts.searchUsername(productConfirm.getUsername());
        this.loginAccount = acc;

        //System.out.println(acc.toCsv());

        //--------------------------------------------------------------------------------------------------------------------------

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Add product completed");

        alert.showAndWait();

        try {
            FXRouter.goTo("list_of_products",loginAccount);

        } catch (IOException e) {
            System.err.println("ไปที่หน้า list_of_products ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }




}
