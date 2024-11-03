package ku.cs.services;

import ku.cs.models.*;

import java.io.*;

public class OrderFileDataSource implements DataSource<OrderList> {

    private String directoryName;
    private String filename;

    private OrderList orders= new OrderList();



    //เขียนจริง
    public OrderFileDataSource(){
        this("Data","OrderList.csv");
    }

    //for test
    public OrderFileDataSource(String directoryName, String filename){
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileNotExist();

    }

    private void initialFileNotExist() {
        File file = new File(directoryName);

        if(!file.exists()){ //ถ้า directory ไม่มีอยู่ให้สร้าง
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
    public void writeData(OrderList orders) {
        //วีธีการเขียน สมมติว่า รับ AccountList มา --> เราจะเขียนข้อมูลทั้งหมด ใน AccountList เลย

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;
        // ป้องกันการเกิด Exception
        try {

            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);


            buffer.write(orders.toCsv());


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
    public OrderList readData() {

        String path = "Data" + File.separator + "OrderList.csv";
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ( (line = buffer.readLine() ) != null ) {
                System.out.printf("line = "+ line);
                    String[] data = line.split(",");
                    if (data.length == 7) {
                    orders.addOrder(new Order(
                            data[0],
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]),
                            Integer.parseInt(data[4]),
                            Double.parseDouble(data[5]),
                            data[6]

                    ));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
    //products จากที่อ่านไฟล์
    public OrderList getAllOrderList() { return orders;}


}
