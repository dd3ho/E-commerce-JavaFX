package ku.cs.models;

public class SellerAccount extends Account {


    //เขียนไฟล์ + new ตอนเปลี่ยนจาก user เป็น seller ใน created store
    public SellerAccount(String username, String name, String lastname, String password , String storeName, String status, String lastLogin,Integer countLogin,String imagePath) {
        super(username, name, lastname, password,storeName,status,lastLogin,countLogin,imagePath);
        setType("Seller");

    }

//    public String getStoreName() {return storeName;}
//    //public void setStoreName(String storeName) {this.storeName = storeName;} -->อ.อุษา

    @Override
    public String toCsv(){
        return getType()+","+getUsername()+","+getName()+","+getLastname()+","+getPassword()+","+getStoreName()+","+getStatus()+","+getLastLogin()+","+getCountLogin()+","+getImagePath();
        //return super.toCsv()+","+storeName -->อ.อุษาแนะนำ
    }

//    public boolean isStoreName(String storeName) {
//        return this.storeName.equals(storeName);
//    }
}
