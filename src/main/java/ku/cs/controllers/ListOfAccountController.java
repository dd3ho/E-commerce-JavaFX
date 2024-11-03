package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.FXRouter;
import ku.cs.models.Account;
import ku.cs.models.AccountList;
import ku.cs.services.AccountFileDataSource;

import java.io.IOException;

public class ListOfAccountController {

    @FXML
    private ListView<Account> accountListView;

    @FXML private Label nameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label storeNameLabel;
    @FXML private Label statusLabel;
    @FXML private Label lastLoginLabel;
    @FXML private ImageView userImageView;

    private AccountFileDataSource dataSource;
    private AccountList accountsList;

    private Account selectedAccount;

    @FXML
    public void initialize(){

        dataSource = new AccountFileDataSource();
        dataSource.readData();
        accountsList = dataSource.getAllAccountList();
        showListView();
        clearSelectedAccount();
        handleSelectedListView();
    }

    private void showListView() {
        //ให้ ListView เรียงตามเวลา login ล่าสุด
        accountsList.sortAccount();
        accountListView.getItems().addAll(accountsList.getAllAccounts());
        accountListView.refresh();
    }

    private void handleSelectedListView() {
        accountListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Account>() {
                    @Override
                    public void changed(ObservableValue<? extends Account> observable,
                                        Account oldValue, Account newValue) {
                        //System.out.println(newValue + " is selected");
                        selectedAccount = newValue;
                        showSelectedAccount(newValue);
                    }
                });
    }

    private void showSelectedAccount(Account account) {
        nameLabel.setText(account.getName());
        lastnameLabel.setText(account.getLastname());
        usernameLabel.setText(account.getUsername());
        statusLabel.setText(account.getStatus());
        storeNameLabel.setText(account.getStoreName());
        lastLoginLabel.setText(account.getLastLogin());
        userImageView.setImage(new Image("file:"+account.getImagePath(), true));

    }


    private void clearSelectedAccount() {
        nameLabel.setText("");
        lastnameLabel.setText("");
        usernameLabel.setText("");
        statusLabel.setText("");
        lastLoginLabel.setText("");
//        userImageView.setImage(new Image("images/user_default.png"));
    }

    @FXML
    public  void handleBanAccountButton(ActionEvent actionEvent){
        //System.out.println(selectedAccount.getType());
        if(selectedAccount.isAdmin(selectedAccount.getType())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("This is Admin don't banned");

            alert.showAndWait();
        }else{
            selectedAccount.setStatus();
            accountListView.refresh();
            showSelectedAccount(selectedAccount);

            //setStatus = Baned
            Account account = accountsList.searchUsername(selectedAccount.getUsername()); //หาว่าชี้ตัวไหนที่อยู่ใน list
            account.setStatus(); //พอชี้เสร็จก็แก้ตัวที่เราชี้
            dataSource.writeData(accountsList);
        }

    }

    @ FXML
    public void handleUnBanAccountButton(ActionEvent actionEvent){
        String unbanned = "Not Banned";
        selectedAccount.setStatus(unbanned);
        selectedAccount.setCountLogin(0);
        accountListView.refresh();
        showSelectedAccount(selectedAccount);

        Account account = accountsList.searchUsername(selectedAccount.getUsername());
        //หาว่าชี้ตัวไหนที่อยู่ใน list
        //setContLogin ให้เป็น 0  เพราะ  unbanned แล้ว
        account.setCountLogin(0); //พอชี้เสร็จก็แก้ตัวที่เราชี้
        dataSource.writeData(accountsList);
    }

    @FXML
    public void handleBackToSystemForAdminButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("system_for_admin");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า system_for_admin ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
