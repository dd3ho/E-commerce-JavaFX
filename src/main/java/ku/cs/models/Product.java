package ku.cs.models;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;

public class Product {
    private String type = "Seller";
    private String username;
    private String storeName;

    private String name;
    private double price;
    private int quantity;
    private String productDetails;
    private String imagePath;
    private LocalDateTime lastUpdateTime;
    private int status;



    //add product

    //csv-->อาจจะลบ
//    public Product(String type,String username,String storeName,String name,double price,int quantity,String productDetails) {
//        super(type, username, storeName);
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        this.productDetails = productDetails;
//        setType("Seller");
//    }

    //listofproduct
    public Product(String username,String storeName,String name){
        this.username=username;
        this.storeName=storeName;
        this.name=name;
    }
    //csv
    public Product(String username, String storeName, String name, double price, int quantity, String productDetails, String imagePath,String lastUpdateTime,int status) {
        this.username=username;
        this.storeName=storeName;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
        this.productDetails=productDetails;
        this.imagePath=imagePath;
        this.lastUpdateTime=LocalDateTime.parse(lastUpdateTime);
        this.status=status;
    }

    //addProduct สร้างตัวแปรชั่วคราว productTemp มา assign ค่า ให้ productForSetImagePath
    public Product(String username, String storeName, String name, double price, int quantity, String productDetails){
        this.username=username;
        this.storeName=storeName;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
        this.productDetails=productDetails;
        setLastUpdateTime();
    }

    public void setName(String name ) { this.name= name; }
    public void setStore(String storeName) { this.storeName=storeName; }
    public void setImagePath(String imagePath) { this.imagePath=imagePath;}
    public void setProductDetails(String productDetails) { this.productDetails=productDetails;}

    public void setLastUpdateTime () {
        this.lastUpdateTime = LocalDateTime.now();
        lastUpdateTime.toString();
    }

    public void setPrice(double price) {this.price=price;}
    public void setQuantity(int quantity) {this.quantity=quantity;}


    public void setType(String type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int getStatus() {
        return status;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getPrice1(){
        return String.valueOf(price);
    }
    public int getQuantity() { return quantity;}
    public String getQuantity1(){return String.valueOf(quantity);}
    public String getProductDetails() {return productDetails;}
    public String getStoreName() {return storeName;}
    public String getImagePath() {return imagePath;}
    public String getLastUpdateTime() {
        return String.valueOf(lastUpdateTime);
    }
    public  LocalDateTime getLastUpdateTime1(){return lastUpdateTime;}

    public String getUsername() {
        return username;
    }



    //check ว่า storeName ที่รับเข้ามา --> เทียบกับ storeName ที่ถูกดึงออกมาจาก product.csv
    public boolean isStoreName(String storeName) {
        return this.storeName.equals(storeName);
    }
    public boolean isName(String name){ return this.name.equals(name); }

    public boolean isNullImagePath(Product product){
        if (product.getImagePath() == null){
            return true; // เป็น Null
        }else{
            return false;
        }
    }

    public  boolean checkQuantity(int amount, int quantity  ) {
        if(quantity>=amount){
            return true;
        }else{
            return false;
        }
    }


    //check ว่าสินค้าเหลือน้อยกว่า status ไหม และไม่เป็น 0
    public boolean checkStatusQuantity(Product product){
        if (product.getQuantity()<product.getStatus() && product.getQuantity()>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String toString(){
        //ใช้ DateTimeFormatter showListview
//        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formatDateTime = lastLogin.format(format);
        return  storeName+","+name;
    }


    public String toCsv() {
        return type+","+username+","+storeName+","+name+","+price+","+quantity+","+productDetails+","+imagePath+","+lastUpdateTime+","+status;
    }





}

