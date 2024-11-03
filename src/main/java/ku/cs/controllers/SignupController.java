package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.models.Account;
import ku.cs.models.AccountList;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

public class SignupController {
    @FXML private TextField nameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField userNameTextField;
    @FXML private PasswordField passwordPasswordField;
    @FXML private PasswordField confirmPasswordPasswordField;
    @FXML private ImageView userImageView;
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    private Account accountForSetImagePath;

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------
    @FXML
    public void initialize() {
       // userImageView.setImage(new Image("/ku/cs/images/user_default.png")); ใช้ไม่ได้
    }


    @FXML
    public void handleUploadImageButton(ActionEvent event) {
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
//                System.out.println("Upload: "+accountForSetImagePath.getImagePath())
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML //ไปที่หน้า system_for_user
    public void handleEnterButton(ActionEvent event) {
        //เขียนอ่านไฟล์
        DataSource<AccountList> dataSource = new AccountFileDataSource();
        AccountList accounts = dataSource.readData();

        //System.out.println(accounts.toCsv());
        // รับข้อมูล จาก nameTextField ข้อมูลที่รับเป็น String เสมอ
        String nameStr = nameTextField.getText(); // ตัวแปร name
        // รับข้อมูล จาก lastNameTextField ข้อมูลที่รับเป็น String เสมอ
        String lastnameStr = lastNameTextField.getText(); // ตัวแปร lastName
        // รับข้อมูล จาก userNameTextField ข้อมูลที่รับเป็น String เสมอ
        String usernameStr = userNameTextField.getText(); // ตัวแปร userName
        // รับข้อมูล จาก PasswordField ข้อมูลที่รับเป็น String เสมอ
        String passwordStr = passwordPasswordField.getText(); // ตัวแปร password
        // รับข้อมูล จาก PasswordField ข้อมูลที่รับเป็น String เสมอ
        String confirmPasswordStr = confirmPasswordPasswordField.getText(); // ตัวแปร confirmPassword




        if ((usernameStr.equals("") || nameStr.equals("") || lastnameStr.equals("") || passwordStr.equals("") || confirmPasswordStr.equals(""))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Please check your information and try again.");

            alert.showAndWait();
        } else {
            //check ช้อมูลซ้ำ --> ว่าใน account list มี usernameStr ซ้ำไหม ถ้าซ้ำจะสมัครไม่ได้
            if (accounts.checkUsername(usernameStr)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("Please check your information and try again.");

                alert.showAndWait();
            }

            //check ว่า name ซ้าไหม ถ้าซ้า alert
            else if (accounts.checkName(nameStr)) {
                //System.out.println("ชื่อซ้ำ");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("Please check your information and try again.");

                alert.showAndWait();
            } else {
                if (!passwordStr.equals(confirmPasswordStr)) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error!!");
                    alert.setHeaderText(null);
                    alert.setContentText("Please check your information and try again.");

                    alert.showAndWait();

                } else {
                    //เขียนไฟล์ --> new Account
                    accounts.addAccount(new Account(usernameStr, nameStr, lastnameStr, passwordStr));
                    //account.getImagePath เป็น user_default.png
                    Account account = accounts.searchUsername(usernameStr); //หาว่าชี้ตัวไหนที่อยู่ใน list

                    //check ว่า images  ไม่เป็น default ถ้าไม่เป็น จะ setImage()
                    if (!(accountForSetImagePath==null)) {
//                        System.out.println("เข้่า if");
                        account.setImagePath(accountForSetImagePath.getImagePath());//พอชี้เสร็จก็แก้ตัวที่เราชี้
                    }else{
//                        System.out.println("เข้า else");
                        account.setImagePath();
                    }
                    account.setLastLogin(); //พอชี้เสร็จก็แก้ตัวที่เราชี้
                    //เขียนไฟล์
                    dataSource.writeData(accounts);

                    try {
                        FXRouter.goTo("login");
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า login ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }
            }

        }
    }

    @FXML //ไปที่หน้า first_page
    public void handleBackButton(ActionEvent event) {
        try {
            FXRouter.goTo("first_page");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า first_page ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
