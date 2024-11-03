package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.FXRouter;
import ku.cs.models.Product;
import ku.cs.models.ProductList;
import ku.cs.models.SellerAccount;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;

import java.io.IOException;

public class ManageStoreController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField statusQtTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private Label qtLabel;

    public SellerAccount loginAccount;
    @FXML
    public void initialize(){
        loginAccount = (SellerAccount) FXRouter.getData();
        clearTextField();
    }


    private void clearTextField() {
        nameTextField.setText("");
        statusQtTextField.setText("");
    }

    public void showData(Product product){
        //System.out.printf(product.getName());
        nameLabel.setText(product.getName());
        qtLabel.setText(String.valueOf(product.getStatus()));
    }

    @FXML
    void handleBackToSystemButton(ActionEvent event) {
        try {
            FXRouter.goTo("system_for_seller");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า seller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void handleSearchProductButton(ActionEvent event){
        String nameStr = nameTextField.getText(); //nameOfProduct

        DataSource<ProductList> dataSource = new ProductFileDataSource();
        ProductList products = dataSource.readData();

       Product product = products.searchStoreNameAndProduct(loginAccount.getStoreName(),nameStr) ;

       if (products.checkStoreNameAndProduct(loginAccount.getStoreName(),nameStr)){
           //System.out.printf("if");
           showData(product);
           product = null;

       }else{
           //System.out.printf("else");
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Error!!");
           alert.setHeaderText(null);
           alert.setContentText("Didn't find your product");

           alert.showAndWait();

           clearTextField();
       }
    }



    @FXML
    void handleEditProductButton(ActionEvent event) {

        int status = Integer.parseInt(statusQtTextField.getText());
        DataSource<ProductList> dataSource = new ProductFileDataSource();
        ProductList products = dataSource.readData();
        Product product = products.searchStoreNameAndProduct(loginAccount.getStoreName(),nameTextField.getText());

        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("Didn't find your product");
            clearTextField();
        } else {
            product.setStatus(status);
            dataSource.writeData(products);

            showData(product);
        }
    }







}
