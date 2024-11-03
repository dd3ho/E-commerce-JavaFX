package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EditProductController {
    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField quantityTextField;
    @FXML private TextField productDetailsTextField;
    @FXML private ImageView productImageView;

    @FXML private TextField statusQuantity;
    public SellerAccount loginAccount;
    private Product productForSetImagePath;


    @FXML
    public void initialize() {
        loginAccount = (SellerAccount) FXRouter.getData();
    }

    @FXML
    public void handleUploadProductButton(ActionEvent event) {
        String nameStr =nameTextField.getText();


        //เขียนอ่านไฟล์
        DataSource<ProductList> dataSource = new ProductFileDataSource();
        ProductList products = dataSource.readData();


        FileChooser chooser = new FileChooser();

        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));


        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));

        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {

                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");

                if (!destDir.exists()) destDir.mkdirs();


                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");

                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );


                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);


                // SET NEW FILE PATH TO IMAGE
                productImageView.setImage(new Image(target.toUri().toString()));


                // type+","+username+","+storeName+","+name+","+price+","+quantity+","+productDetails+","+imagePath+","+lastUpdateTime+","+status
                Product productForSetImagePath = new Product("Seller","Store","name",0,0,"productDetail","-",String.valueOf(LocalDateTime.now()),0);
                productForSetImagePath.setImagePath(destDir + "/" + filename);
                this.productForSetImagePath=productForSetImagePath;
                //-------------------------------------------------
                Product product = products.searchStoreNameAndProduct(loginAccount.getStoreName(),nameStr);
                product.setImagePath(destDir + "/" + filename);

                //เขียนไฟล์
                dataSource.writeData(products);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Add image completed");

                alert.showAndWait();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
  }



    @FXML
    public void handleEnterEditProductButton() {
        String nameStr =nameTextField.getText();
        double price = Double.parseDouble(priceTextField.getText());
        int quantity = Integer.parseInt(quantityTextField.getText());
        String pdStr=productDetailsTextField.getText();

        //เขียนอ่านไฟล์
        DataSource<ProductList> dataSource = new ProductFileDataSource();
        ProductList products = dataSource.readData();

        //----------------------------------------------------------------------------------------------------------------
        Product product = products.searchStoreNameAndProduct(loginAccount.getStoreName(),nameStr);
        if(products.checkStoreNameAndProduct(loginAccount.getStoreName(),nameStr)){

            product.setImagePath(productForSetImagePath.getImagePath());
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setProductDetails(pdStr);
            product.setStatus(Integer.parseInt(statusQuantity.getText()));
            product.getLastUpdateTime();

            dataSource.writeData(products);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Edit Product Already");

            alert.showAndWait();

            try {
                FXRouter.goTo("system_for_seller", loginAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า system_for_seller ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }


        }else{

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Didn't find The Product please check Information again");

            alert.showAndWait();

        }
        clearTextField();
    }

    private void clearTextField() {
        nameTextField.setText("");
        priceTextField.setText("");
        quantityTextField.setText("");
        productDetailsTextField.setText("");
        productImageView.setImage(null);
        productForSetImagePath.setImagePath("");
        statusQuantity.setText("");
    }

    @FXML
    public void handleBackToSellerButton() {
        try {
            FXRouter.goTo("add_product", (SellerAccount)loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
