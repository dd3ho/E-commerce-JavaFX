package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static final String CURRENCY = "$";
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Shop Town",800, 600);
        configRoute();
        FXRouter.goTo("first_page");
    }

    private void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("add_product", packageStr+"add_product.fxml");
        FXRouter.when("change_password", packageStr+"change_password.fxml");
        FXRouter.when("confirm_add_product",packageStr+"confirm_add_product.fxml");
        FXRouter.when("create_store", packageStr+"create_store.fxml");
        FXRouter.when("first_page", packageStr+"first_page.fxml");
        FXRouter.when("help", packageStr+"help.fxml");
        FXRouter.when("item", packageStr+"item.fxml");
        FXRouter.when("list_of_account", packageStr+"list_of_account.fxml");
        FXRouter.when("list_of_products", packageStr+"list_of_products.fxml");
        FXRouter.when("login", packageStr+"login.fxml");
        FXRouter.when("manage_store", packageStr+"manage_store.fxml");
        FXRouter.when("our_profile", packageStr+"our_profile.fxml");
        FXRouter.when("purchase_order", packageStr+"purchase_order.fxml");
        FXRouter.when("seller_help", packageStr+"seller_help.fxml");
        FXRouter.when("seller_help_change_password", packageStr+"seller_help_change_password.fxml");
        FXRouter.when("seller_help_add_product", packageStr+"seller_help_add_product.fxml");
        FXRouter.when("seller_help_edit_product", packageStr+"seller_help_edit_product.fxml");
        FXRouter.when("seller_help_list_of_products", packageStr+"seller_help_list_of_products.fxml");
        FXRouter.when("shop", packageStr+"shop.fxml");
        FXRouter.when("signup", packageStr+"signup.fxml");
        FXRouter.when("store", packageStr+"store.fxml");
        FXRouter.when("system_for_admin", packageStr+"system_for_admin.fxml");
        FXRouter.when("system_for_seller",packageStr+"system_for_seller.fxml");
        FXRouter.when("system_for_user",packageStr+"system_for_user.fxml");
        FXRouter.when("user_help", packageStr+"user_help.fxml");
        FXRouter.when("user_help_change_password", packageStr+"user_help_change_password.fxml");
        FXRouter.when("user_help_create_store", packageStr+"user_help_create_store.fxml");
        FXRouter.when("user_help_purchase_order", packageStr+"user_help_purchase_order.fxml");
        FXRouter.when("user_help_shop", packageStr+"user_help_shop.fxml");
        FXRouter.when("edit_product", packageStr+"edit_product.fxml");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }

}