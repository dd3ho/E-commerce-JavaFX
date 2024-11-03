package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.models.Product;
import ku.cs.services.DataSource;
import ku.cs.services.ProductFileDataSource;

import java.io.IOException;
import java.time.LocalDateTime;

public class ListOfProductsController {
    @FXML private ListView<Product> productListView;

    //-------------------------------------------------------------------------------------------------------------------------------
    @FXML private Label storeNameLabel;
    @FXML private Label nameLabel;
    @FXML private Label priceLabel;
    @FXML private Label qtLabel;
    @FXML private Label pdLabel;
    @FXML private ImageView productImageView;
    @FXML private Label statusQuantityLabel;

    //--------------------------------------------------------------------------------------------------------------------------------
    public SellerAccount loginAccount; //รับ login

    private ProductFileDataSource dataSource;
    private ProductList products;

    //---------------------------------------------------------------------------------------------------------
    public Product selectedProduct;

    //----------------------------------------------------------------------------------------------------------------------------------

    @FXML
    public void initialize(){
        //รับ login account
        loginAccount=(SellerAccount)FXRouter.getData();

//        System.out.println((SellerAccount)FXRouter.getData());
//        System.out.println(FXRouter.getData());
//        System.out.println(loginAccount.getStoreName());

        dataSource = new ProductFileDataSource("Data","StockOfProduct.csv");
        dataSource.readData();
        dataSource.writeData(products);

        //------------------------------------
        products = dataSource.getAllProductList();

////        Product product = new Product("username6","store6","room spray",150,20,"300ml");
        Product productTemp = products.searchStoreName(loginAccount.getStoreName());
        products.removeProduct(productTemp);
        dataSource.writeData(products); //เขียนไฟล์ใหม่

        showListview();
        clearSelectedAccount();
        handleSelectedListview();
    }


    private void showListview() {
        //ให้ ListView เรียงตามเวลา login ล่าสุด
        productListView.getItems().addAll(products.getAllProducts());
        productListView.refresh();

    }
    private void handleSelectedListview() {
        productListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable,
                                        Product oldValue, Product newValue) {
                        //System.out.println(newValue + " is selected");
                        selectedProduct = newValue;
                        showSelectedAccount(newValue);
                    }
                });
    }


    private void showSelectedAccount(Product product) {
        storeNameLabel.setText(loginAccount.getStoreName());
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice1());
        qtLabel.setText(product.getQuantity1());
        pdLabel.setText(product.getProductDetails());

        productImageView.setImage(new Image("file:"+product.getImagePath(), true));

        //ใช้ check status Quantity ตอนที่ user กรอกในหน้า manageStore หรือ ใน หน้า add Product
        if(selectedProduct.checkStatusQuantity(selectedProduct)){
            statusQuantityLabel.setText("น้อย");
        }else{
            statusQuantityLabel.setText("มาก");
        }
        //-------------------------------------------------------------------------------
    }

    private void clearSelectedAccount() {
        storeNameLabel.setText("");
        nameLabel.setText("");
        priceLabel.setText("");
        qtLabel.setText("");
        pdLabel.setText("");
        statusQuantityLabel.setText("");
    }

    @FXML //ไปที่หน้า system_for_seller
    public void handleBackToSellerButton(ActionEvent event){
        try {
            FXRouter.goTo("system_for_seller",loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า system_for_seller ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    public void handleEditProductButton(ActionEvent event){
        //System.out.printf("login" + loginAccount.toCsv());
        try {
            FXRouter.goTo("edit_product",loginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า edit_product ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
