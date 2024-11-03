package ku.cs.models;

import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// super class
public class Account {
    private String type;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String status = "Not Banned";
    private LocalDateTime lastLogin ;
    private Integer countLogin ;
    private String imagePath;
    private  String storeName;


    //ใข้ตอน newUser
    public Account(String username,String name,String lastname,String password){
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        setType("User");
        this.countLogin=0;
        this.imagePath ="images/user_default.png";
        this.storeName = "No";
    }

    //constructor ต้องตรงกับ file csv (ตำเเหน่ง) --> ไว้เขียนอ่านไฟล์ csv
    public Account(String username,String name,String lastname,String password,String storeName, String status,String lastLogin,Integer countLogin,String imagePath){
        this(username, name, lastname, password);
        this.status = status;
        this.lastLogin = LocalDateTime.parse(lastLogin);
        this.countLogin = countLogin;
        this.imagePath = imagePath;
        this.storeName = storeName;

    }
    //ใช้สำหรับ extend ของ หน้า admin
    public Account(String username, String password,String status,String lastLogin,int countLogin) {
        this.username = username;
        this.password = password;
        this.status=status;
        this.lastLogin = LocalDateTime.parse(lastLogin);
        this.countLogin = countLogin;

    }

//    public Account(String type, String username) {
//        this.type=type;
//        this.username=username;
//    }


    //check ว่า lastname ที่รับเข้ามา --> เทียบกับ คนคนนึง ดึงออกมา
    public boolean isLastname(String lastname) {
        return this.lastname.equals(lastname);
    }
    //check ว่า username ที่รับเข้ามา --> เทียบกับ คนคนนึง ดึงออกมา
    public boolean isUsername(String username) {
        return this.username.equals(username);// return true-false
    }
    //check ชื่อซ้ำ
    public boolean isDuplicateName(String name) {
        return this.name.equals(name);
    }
    //check ว่า password ที่รับเข้ามา --> เทียบกับ คนคนนึง ดึงออกมา
    public boolean isPassword(String password){
        return this.password.equals(password);
    }
    //check ว่า status ที่รับเข้ามา --> ถูกแบนหรือไม่
    public boolean isBanned() {return this.status.equals("Banned"); }
    public  boolean isAdmin(String type){
        return this.type.equals("Admin");
    }
    //check ว่า ImagePath เป็น default ไหม
    public boolean isDefaultImagePath(){
        return this.imagePath.equals("images/default.png");
    }

    public boolean isStoreName(String storeName) { return this.storeName.equals(storeName); }

    public String getType(){return type;}
    public String getName() {return name;}
    public String getLastname() {return lastname;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getStatus() {
        return status;
    }

    //AccountList ใช้ตอน newSeller, ตรง SellerAccount toCsv
    public String getLastLogin() { return lastLogin.toString(); }
    //จำนวนครั้งที่ user ที่ถูกแบนพยายาม จะเข้า สู่ระบบ
    public Integer getCountLogin() { return countLogin; }

    public String getImagePath() {
        return imagePath;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //เอาไว้ให้ admin กด baned
    public void setStatus() {
        this.status = "Banned";
    }
    public void setStatus(String unbanned){
        this.status = "Not Banned";
    }

    //set เวลาที่กด login ล่าสุด
    public void setLastLogin(){
        this.lastLogin = LocalDateTime.now();
        lastLogin.toString();
    }

    //set type เพื่อกำหนดว่าเป็น account ประเภทไหน
    //setType คนแต่ละประเภท
    public  void setType(String type){
        this.type = type;
    }

    //ใช้ตอนเปลี่ยนรหัสผ่าน
    public void setPassword(String newPasswordStr) {
        this.password = newPasswordStr;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    //setCountLogin ที่หน้า login สำหรับผู้ใช้ที่โดน ban พยายามจะ Login
    public void setCountLogin() {
        this.countLogin++;
    }

    //set ที่ปุ่ม unbanned หน้า login
    public void setCountLogin(Integer countLogin) {
        this.countLogin = countLogin;
    }

    //set รูป(imagePath) UserDefault กรณีที่ register แล้ว user ไม่ upload รูป
    public void setImagePath() {
        this.imagePath = "images/user_default.png";
    }

    //set รูป(imagePath) กรณีที่ user กดปุ่ม อัพโหลดรูป
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    //    // check ว่า password กับ confirmPassword ตรงกันไหม
//    public boolean checkConfirmPassword(String password, String confirmPassword){
//        if (this.confirmPassword.equals(password)){
//            return true;
//        }
//        return false;
//    }

    public String toCsv() {
        return type+","+username+","+name+","+lastname+","+password+","+storeName+","+status+","+lastLogin+","+countLogin+","+imagePath;
    }

    @Override
    public String toString(){
        //ใช้ DateTimeFormatter showListview
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = lastLogin.format(format);
        return  type+","+username+","+status+","+formatDateTime+","+countLogin;
    }



}