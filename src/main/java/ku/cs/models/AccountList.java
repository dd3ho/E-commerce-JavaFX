package ku.cs.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// เก็บข้อมูลของ users แสดงให้ admin
public class AccountList {

    private ArrayList<Account> accounts;

    private ArrayList<SellerAccount> sellerAccounts;



    public AccountList(){
        accounts = new ArrayList<>();
        sellerAccounts = new ArrayList<>(); //ใช้หน้า createdStore

    }

    //เพิ่ม user
    public void addAccount(Account account){
        accounts.add(account);
    }

    public  void addSellerAccounts(SellerAccount account){sellerAccounts.add(account);}


    // เรียกดู all users account

    public ArrayList<SellerAccount> getSellerAccounts() { return sellerAccounts; }
    public ArrayList<Account> getAllAccounts(){return accounts; }



    public Account searchUsername(String username){
        for(Account account:this.accounts){
            if(account.isUsername(username)){
                return account;
            }
        }return null; //วน account แล้วไม่เจอ username
    }

    //check username ซ้ำ
    public boolean checkUsername(String username){
        for(Account account:this.accounts){
            if(account.isUsername(username)){
                return true;
            }
        }return false; //วน account แล้วไม่เจอ username

    }

    public boolean checkName(String name){
        for(Account account:this.accounts){
            System.out.println(account.getUsername());

            if(account.getType().equals("Admin")){
                return false;
            }
            if(account.isDuplicateName(name)){
                return true;
            }
        }return false; //วน account แล้วไม่เจอ username
    }

    public boolean checkPassword(String password){
        for(Account account:this.accounts){
            if(account.isPassword(password)){
                return true;
            }
        }return false;
    }

    //ใช้ หน้า created store
    public boolean checkStoreName(String storeName){
        //สร้าง ArrayList ของ seller เพิ่ม เอาไว้ check ชื่อร้าน สร้าง ArrayList เพิ้่ม ต้องไป addSellerAccount ตรง readdata() ใน AccountFileDataSource ด้วย
        for(SellerAccount sellerAccount:this.sellerAccounts){
            System.out.println(sellerAccount.getStoreName());
            if(sellerAccount.isStoreName(storeName)){
                return true;
            }
        }return false; //วน account แล้วไม่เจอ storeName
    }

//    public boolean checkStatusBanned(String status){
//        for (Account account: this.accounts){
//            if (account.isStatus(status)){
//                return true;
//            }
//        }return false;
//    }


    public void changeType(String username,String storeName){
        Account acc = pop(username);
        SellerAccount sellerAccount = new SellerAccount(acc.getUsername(),acc.getName(),acc.getLastname(),acc.getPassword(),acc.getStoreName(),acc.getStatus(),acc.getLastLogin(),acc.getCountLogin(), acc.getImagePath());
        accounts.add(sellerAccount);
    }

    //ดึง account ที่username เดียวกับพารามิเตอร์ ออกมา --> ลบ account username นั้นออกจาก list
    //return account นั้น ไปทำ method changeType
    public Account pop(String username){
        Account acc = searchUsername(username);
        accounts.remove(acc);
        return acc;
    }

    //sort ตามเวลา login ล่าสุด
    public void sortAccount(){
        Comparator<Account> comparator = (c1, c2) -> {
            return c2.getLastLogin().compareTo(c1.getLastLogin());
        };
        Collections.sort(accounts, comparator);
    }

    // check ว่า username ซ้ำกันมั้ย ถ้าซ้ำ = ture-->เวลาจะ regis
    // ต้องมา check ใน list ว่ามี username แล้วหรือยัง ถ้ามีแล้วจะไม่ add เข้า list
    //และไม่เอาไปเขียนไฟล์


    public boolean checkAccountIfExist(String username) {
//        for (SellerAccount account : sellerAccounts) {
//            if (account.getUsername().equals(username)) {
//                return true;
//            }
//        }
        for (Account account : accounts ) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public String toCsv() {
        String result = "";
        for (Account account : this.accounts){
            result += account.toCsv() + "\n";
        }
        return result;
    }

}