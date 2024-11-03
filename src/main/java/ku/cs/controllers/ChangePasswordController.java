package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import ku.cs.FXRouter;
import ku.cs.models.Account;
import ku.cs.models.AccountList;
import ku.cs.services.AccountFileDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;

public class ChangePasswordController {

    @FXML private PasswordField currentPasswordPasswordField;
    @FXML private PasswordField newPasswordPasswordField;
    @FXML private PasswordField confirmPasswordPasswordField;

    private AccountFileDataSource dataSource;
    private AccountList accountsList;

    //ส่งข้อมูลมาต่อกันเรื่อยจาก หน้า login
    private Account loginAccount;
    @FXML
    public void initialize(){
        loginAccount = (Account) FXRouter.getData();
    }



    @FXML
    public void handleEnterChangPasswordSellerButton(ActionEvent event) {

        dataSource = new AccountFileDataSource();
        dataSource.readData();

        accountsList = dataSource.getAllAccountList();


        String currentPasswordStr= currentPasswordPasswordField.getText();
        String newPasswordStr = newPasswordPasswordField.getText();
        String confirmPasswordStr = confirmPasswordPasswordField.getText();

        if(currentPasswordStr.equals("") || newPasswordStr.equals("") || confirmPasswordStr.equals("")){


        }else{
            //กรอก currentPassword ไม่ตรงกับ password ใน data
            if (!(loginAccount.getPassword().equals(currentPasswordStr))){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("Current password failed, Enter a valid password and try again.");

                alert.showAndWait();

                //กรอก newPassword  ไม่ตรงกับ confirmPassword
            }else if( !(newPasswordStr.equals(confirmPasswordStr)) ){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("You must enter the same password twice in order to confirm password.");

                alert.showAndWait();

                //กรอก currentPassword ตรงกับ newPassword
            }else if(currentPasswordStr.equals(newPasswordStr)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("New password must differ from current password.");

                alert.showAndWait();

                //กรอกถูก --> เราจะ change password
            }else{
                //System.out.println(loginAccount.getPassword());

                //set password ให้ loginAccount
                loginAccount.setPassword(newPasswordStr);
                //System.out.println(loginAccount.getPassword());
                //System.out.println(loginAccount.getUsername());


                // set password ใน account ใน csv ใหม่
                Account account = accountsList.searchUsername(loginAccount.getUsername());
                account.setPassword(newPasswordStr); //พอชี้เสร็จก็แก้ตัวที่เราชี้
                //System.out.println(account.getPassword());
                dataSource.writeData(accountsList);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Password change success.");

                alert.showAndWait();


                //get loginAccount มาดูว่า เป็นคนประเภทไหน เเต่อิมิวเป็นคนแปลก
                // -->แล้วส่งไปหน้า system ของคนนั่นๆ
                if(loginAccount.getType().equals("Admin")){
                    try {
                        FXRouter.goTo("system_for_admin",loginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า System for admin ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }
                if(loginAccount.getType().equals("Seller")){
                    try {
                        FXRouter.goTo("system_for_seller",loginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า System for seller ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }
                if(loginAccount.getType().equals("User")){
                    try {
                        FXRouter.goTo("system_for_user",loginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า System for userไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }
            }

        }
    }


//        for (Account account: accounts.getAllAccounts() ){
//            if(currentPasswordStr.equals("") || newPasswordStr.equals("") || confirmPasswordStr.equals("") ){
//
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Error!!");
//                alert.setHeaderText(null);
//                alert.setContentText("Please check your information and try again.");
//
//                alert.showAndWait();
//
//            }else{
//                //กรอก currentPassword ไม่ตรงกับ password ใน data
//                if (!currentPasswordStr.equals(account.getPassword())){
//
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Error!!");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Current password failed, Enter a valid password and try again.");
//
//                    alert.showAndWait();
//
//                //กรอก newPassword  ไม่ตรงกับ confirmPassword
//                }else if(!newPasswordStr.equals(confirmPasswordStr)){
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Error!!");
//                    alert.setHeaderText(null);
//                    alert.setContentText("You must enter the same password twice in order to confirm password.");
//
//                    alert.showAndWait();
//
//                //กรอก currentPassword ตรงกับ newPassword
//                }else if(currentPasswordStr.equals(newPasswordStr)){
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Error!!");
//                    alert.setHeaderText(null);
//                    alert.setContentText("New password must differ from current password.");
//
//                    alert.showAndWait();
//
//                }else{
//
//
//                    // เช็คว่าเมื่อเปลี่ยน password เสร็จ
//                    // -->  หา type ของ acccount นั้น เพื่อส่ง ไปsystem ของแต่ละฟห้า
//                    // --> จะไปอยู่หน้าของผู้ซื้อ ผู้ขาย หรือแอดมิน
//
//                    if (){
//                        try {
//                            FXRouter.goTo("");
//                        } catch (IOException e) {
//                            System.err.println("ไปที่หน้า System for ไม่ได้");
//                            System.err.println("ให้ตรวจสอบการกำหนด route");
//                        }
//                    }
//                }
//            }
//        }

    @FXML
    public void handleBackButton(ActionEvent event){
        if(loginAccount.getType().equals("Admin")){
            try {
                FXRouter.goTo("system_for_admin",loginAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า system for admin ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        if(loginAccount.getType().equals("Seller")){
            try {
                FXRouter.goTo("system for_seller",loginAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า system for seller ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
        if(loginAccount.getType().equals("User")){
            try {
                FXRouter.goTo("system_for_user",loginAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า system for user ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
