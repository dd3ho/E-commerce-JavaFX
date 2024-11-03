package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.models.Account;
import ku.cs.models.AccountList;
import ku.cs.models.SellerAccount;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class SystemForSellerController {
    @FXML private Label typeLabel;
    @FXML private Label storeLabel;
    @FXML public ImageView userImageView;

    public SellerAccount loginAccount;
    //--------------------------------------------------------
    private Account accountForSetImagePath;

    //--------------------------------------------------------

    @FXML
    public void initialize(){
        loginAccount = (SellerAccount) FXRouter.getData();
        showAccountData(loginAccount);
    }

    private void showAccountData(SellerAccount loginAccount){
        typeLabel.setText(loginAccount.getType());
        storeLabel.setText(loginAccount.getStoreName());
        userImageView.setImage(new Image("file:"+loginAccount.getImagePath(), true));
    }


    //ไปหน้าเปลี่ยน password ของ seller
    @FXML
    public void handleChangePasswordSellerButton(ActionEvent event){
        try {
            FXRouter.goTo("change_password", loginAccount);
        } catch (IOException e){
            System.err.println("ไปที่หน้า change_password ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleListOfProductsButton(ActionEvent event){
        try {
            FXRouter.goTo("list_of_products", (SellerAccount) loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า List of products ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleUploadButton(ActionEvent event){
        //เขียนอ่านไฟล์
        DataSource<AccountList> dataSource = new AccountFileDataSource();
        AccountList accounts = dataSource.readData();


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
                userImageView.setImage(new Image(target.toUri().toString()));

                //setImagePath
                Account accountForSetImagePath = new Account("usernameSetImage","nameSetImage","lastnameSetImage","0");
                accountForSetImagePath.setImagePath(destDir + "/" + filename);
                this.accountForSetImagePath=accountForSetImagePath;
                //System.out.println("Upload: "+accountForSetImagePath.getImagePath());

                Account account = accounts.searchUsername(loginAccount.getUsername());
                account.setImagePath(accountForSetImagePath.getImagePath());
                //เขียนไฟล์
                dataSource.writeData(accounts);

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

    //ไปหน้า add product
    @FXML
    public void handleAddProductSellerButton(ActionEvent event){
        try {
            FXRouter.goTo("add_product", (SellerAccount)loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า add product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //ไปหน้า Manage store seller
    @FXML
    public void handleManageStoreSellerButton(ActionEvent event){
        try {
            FXRouter.goTo("manage_store", loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า manage_store_seller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    public void handleBackButton(ActionEvent event){
        try {
            FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า First_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }



}