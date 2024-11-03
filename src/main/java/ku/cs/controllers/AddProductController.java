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
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class AddProductController {

    @FXML private TextField nameTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField quantityTextField;
    @FXML private TextField productDetailsTextField;
    @FXML private ImageView productImageView;
    @FXML private TextField statusQuantity;

    private SellerAccount loginAccount;
    private Product productForSetImagePath = new Product("test","test","test",0,0,"test");

    public Product productConfirm;

    @FXML
    public  void initialize(){
        loginAccount=(SellerAccount) FXRouter.getData();
    }

    public void handleUploadProductButton(ActionEvent event) {
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
                this.productImageView.setImage(new Image(target.toUri().toString()));

                //setImagePath
                //productForSetImagePath = new Product("test","test","test",0,0,"test"); //local

                productForSetImagePath.setImagePath(destDir + "/" + filename);
                productForSetImagePath.setLastUpdateTime();
                //System.out.println("Upload: "+accountForSetImagePath.getImagePath());

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearTextField() {
        nameTextField.setText("");
        priceTextField.setText("");
        quantityTextField.setText("");
        productDetailsTextField.setText("");
        productImageView.setImage(null); //clearImageView
        statusQuantity.setText("");
    }

    //ปุ่ม enter ไว้ add สินค้า ไปหน้าร้านค้า
    public void handleEnterAddProductButton(ActionEvent event) {
        //เขียนอ่านไฟล์
        DataSource<ProductList> dataSource = new ProductFileDataSource();
        ProductList products = dataSource.readData();

        productForSetImagePath.setLastUpdateTime();

        // รับข้อมูลจาก nameTextField ข้อมูลที่รับเป็น String เสมอ
        String nameStr = nameTextField.getText(); //ตัวแปร name
        //รับ priceStr เป็น string แล้ว เปลี่ยนให้เป็น double
        String priceStr = priceTextField.getText();
        double price = Double.parseDouble(priceStr); //เอาตัวแปร price ส่งให้ product
        //รับ quantity เป็น String แล้วแปลงเป็น int
        String quantityStr = quantityTextField.getText();
        int quantity = Integer.parseInt(quantityStr); //ตัวแปร quantity ส่งให้product
        // รับ productDetails เป็o String
        String productDetailStr = productDetailsTextField.getText();

        int status = Integer.parseInt(statusQuantity.getText());

        //สร้าง product จาก text field ที่ user กรอก มา
        //อันนี้ต้องแก้ storeName ส่งข้อมูล store มาจาก seller account --> แก้แล้ว

        //เขียนไฟล์
        //System.out.println(productForSetImagePath.getImagePath());


        if (productForSetImagePath.isNullImagePath(productForSetImagePath)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Please add image.");

            alert.showAndWait();

        } else {

            //มีสินค้าในร้านนี้อยู่เเล้ว
            if ( products.checkStoreNameAndProduct(loginAccount.getStoreName(),nameStr) ) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("This product already have you can setting in Edit Product Menu.");

                alert.showAndWait();

            } else {

//                 System.out.println("else: ");

                //products.addProduct(new Product(loginAccount.getUsername(), loginAccount.getStoreName(), nameStr, price, quantity, productDetailStr, productForSetImagePath.getImagePath(), productForSetImagePath.getLastUpdateTime(), status));
                this.productConfirm = new Product (loginAccount.getUsername(), loginAccount.getStoreName(),nameStr,price,quantity,productDetailStr,productForSetImagePath.getImagePath(),productForSetImagePath.getLastUpdateTime(),status);
                //-------------------------------------------------------------------------------------------------------------------------
                productForSetImagePath.setImagePath(null); //set null ไว้สำหรบตอน add รูปรอบใหม่

//                System.out.println(productConfirm.toCsv());

                try {

                    FXRouter.goTo("confirm_add_product",productConfirm);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า confirm_add_product ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
        clearTextField();
    }



    @FXML //ไปที่หน้า system_for_seller
    public void handleBackToSellerButton(ActionEvent event){
        try {
            FXRouter.goTo("system_for_seller", loginAccount );
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
