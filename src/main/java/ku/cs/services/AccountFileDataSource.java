package ku.cs.services;

import ku.cs.models.Account;
import ku.cs.models.AccountList;
import ku.cs.models.AdminAccount;
import ku.cs.models.SellerAccount;

import java.io.*;
import java.time.LocalDateTime;

public class AccountFileDataSource implements DataSource<AccountList>{

    private String directoryName;
    private String filename;

    private AccountList accountList = new AccountList();
    private AccountList sellerAccountList = new AccountList();


    public AccountFileDataSource(){

        //directoryName = "csv-data"; //directory จริง ชื่อว่า csv-data
        //fileName = "weapon.csv"; //fileName ที่ทำงานกับ WeaponList
        //มีoverload constructor --> ใช้ chain
        //production file
        this("Data","Accounts.csv");
    }

    //for test
    public AccountFileDataSource(String directoryName, String filename){
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileNotExist();

    }

    private void initialFileNotExist() {
        File file = new File(directoryName);

        if(!file.exists()){ //ถ้าdirectory ไม่มีอยู่ให้สร้าง
            file.mkdir();
        }
        //check file --> ต้องการ path
        String path = directoryName+File.separator+filename;

        file = new File(path); //ชื่อ file.csv

        //ถ้าไม่มี file ให้สร้าง file
        if(!file.exists()){
            try {
                file.createNewFile();

            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void writeData(AccountList accountList) {
        //วีธีการเขียน สมมติว่า รับ AccountList มา --> เราจะเขียนข้อมูลทั้งหมด ใน AccountList เลย

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;
        // ป้องกันการเกิด Exception
        try {
            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(accountList.toCsv());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public AccountList readData() {
        String path = "Data"+File.separator+"Accounts.csv";
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";

            while( (line = buffer.readLine()) != null){

                String[] data = line.split(",");
                String type = data[0];

                if(type.equals("Admin")){
                    accountList.addAccount(new AdminAccount(
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            Integer.parseInt(data[5])
                    ));

                }
                if(type.equals("User")){
                    //System.out.println(data[0]+data[1]+data[2]+data[3]+data[4]+data[5]+data[6]);
                    accountList.addAccount(new Account(
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[5],
                            data[6],
                            data[7],
                            Integer.parseInt(data[8]),
                            data[9]
                    ));
                }
                if(type.equals("Seller")){
                    accountList.addAccount(new SellerAccount(
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[5],
                            data[6],
                            data[7],
                            Integer.parseInt(data[8]),
                            data[9]
                    ));
                    accountList.addSellerAccounts(new SellerAccount(
                            data[1],
                            data[2],
                            data[3],
                            data[4],
                            data[5],
                            data[6],
                            data[7],
                            Integer.parseInt(data[8]),
                            data[9]
                    ));

                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accountList;
    }


    //accountList จากที่อ่านไฟล์
    public AccountList getAllAccountList(){
        return accountList;
    }

}

