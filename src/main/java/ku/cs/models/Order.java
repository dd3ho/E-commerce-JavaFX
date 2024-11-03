package ku.cs.models;

public class Order {
    //จากShop
    //------------------------------------------
    private String username; //ชื่อผู้ซื้อ
    private String name; //ชื่อสินค้า
    private String storeName; //ชื่อร้านค้า
    private double unitPrice; // ราคาต่อชิ้น
    private int amount; // จำนวนชิ้นที่ถูกซื้อ

    //จากหน้าสรุปรายการสินค้า
    //----------------------------------------------
    private double total; // ราคาที่ต้องจ่าย
    private String tracking; // เอาไว้ show ในส่วนของ user

    //เขียน Csv
    public Order(String username,String storeName,String name,double unitPrice,int amount,double total,String tracking){
        this.username=username;
        this.storeName=storeName;
        this.name=name;
        this.unitPrice=unitPrice;
        this.amount=amount;
        this.total=total;
        this.tracking=tracking;

    }
    public Order(String username,String name, double unitPrice, int amount, double total){
        this.username=username;
        this.name = name;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.total = total;
    }

    public void setName(String name) { this.name = name; }

    public void setStoreName(String storeName) { this.storeName = storeName; }

    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }

    public void setAmount(int amount) { this.amount = amount;}

    public String getName() { return name; }

    public double getUnitPrice() { return unitPrice; }

    public String getUnitPrice1() {
        return String.valueOf(unitPrice);
    }

    public int getAmount() { return amount; }

    public double getTotal() { return total; }

    public double calculateTotal(Order order){
        return total = getUnitPrice()* getAmount();
    }

    public void createTracking(String tracking){}

    public void setUsername(String username) {
        this.username=username;
    }

//    public boolean checkTracking(String tracking){
//        if(){
//
//        }else{
//            return false;
//        }
//    }


    //username account ที่สั่งซื้อ
    public String getUsername() {
        return username;
    }

    public String toCsv() {
        return username+","+storeName+","+name+","+unitPrice+","+amount+","+total+","+tracking;
    }


    public void setTotal(double total) { this.total=total; }

    public String getStoreName() { return storeName; }
}