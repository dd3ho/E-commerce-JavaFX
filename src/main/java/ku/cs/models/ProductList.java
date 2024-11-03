package ku.cs.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProductList {

    private ArrayList<Product> products;//เพิ่ม field  products เพื่อเก็บข้อมูล ArrayList ของ Product
    private ArrayList<Product> productsTemp = new ArrayList<>();

    public ProductList() {
        // ใช้ new เพื่อสร้าง instance ของ ArrayList
        products = new ArrayList<>();
    }

    //เพิ่ม product
    public void addProduct(Product product) {
        //เรียก method add จาก ArrayList เพื่อเพิ่มข้อมูล ใน products
        products.add(product);
    }

    //ใช้หน้า createdStore
    //check ว่า storeName ที่รับเข้ามามีใน List ไหม
    public Product searchStoreName(String storeName) {
        for (Product product : this.products) {
            if (product.isStoreName(storeName)) {
                return product; //มี
            }
        }
        return null; //วน product แล้วไม่เจอ storeName
    }

    //ใช้หน้า createdStore
    //ถ้าไม่ใช่ storeName ที่รับมา เราจะลบ storeName ออก
    public ArrayList removeProduct (Product product){
            String storeName = product.getStoreName(); //storeName ที่จะไม่ลบ
//        ArrayList<Product> productsTemp = new ArrayList<>();
            for (int i = 0; i < products.size(); i++) {
                if ((products.get(i).getStoreName().equals(storeName))) {
                    productsTemp.add(products.get(i));
                }
            }
            products = productsTemp;
            return products;
        }

//    public int sizeOfProductList(ArrayList productList) {
//        int tempt = productList.size();
//        return tempt;
//    }

    //for SetLastProductUpdate ตอน Add + update info
    // แล้ว --> return product ตัวนั้นมา ใน productList  มา set ค่า

    public Product searchStoreNameAndProduct(String storeName,String name){
        for(Product product:this.products){
            if(product.isStoreName(storeName) && product.isName(name)){
                return product;
            }
        }return null; //วน account แล้วไม่เจอ username
    }

    public boolean checkStoreNameAndProduct(String storeName,String name){
        for(Product product:this.products){
            if(product.isStoreName(storeName) && product.isName(name)){
                return true;
            }
        }return false; //วน account แล้วไม่เจอ username
    }





        //.remove(object) ใช้กับ for loop ไม่ได้ ทั้ง foreach กับ for i
        //        for(Product deleteProduct: products){
//            if(!(deleteProduct.getStoreName().equals(storeName))) {
////                productList.remove(deleteProduct);
//                products.remove(deleteProduct); ไม่ได้
//
//            }
//        }

        //method คืนค่า ArrayList products
        public ArrayList<Product> getAllProducts () {
            return products;
        }


        //sort ตามเวลา add สินค้าล่าสุด ล่าสุด
        public void sortByTime () {
            Comparator<Product> comparator = (c1, c2) -> {
                return c2.getLastUpdateTime1().compareTo(c1.getLastUpdateTime1());
            };
            Collections.sort(products, comparator);
        }

//    //sort ตามเวลา add สินค้าล่าสุด ล่าสุด
//    public void sortByPriceHight(){
//        Comparator<Product> comparator = (c1, c2) -> {
//            return c2.getPrice().comps
//        };
//        Collections.sort(products, comparator);
//    }
//
//    //sort ตามเวลา add สินค้าล่าสุด ล่าสุด
//    public void sortByPriceLow(){
//        Comparator<Product> comparator = (c1, c2) -> {
//            return c2.getLastUpdateTime1().compareTo(c1.getLastUpdateTime1());
//        };
//        Collections.sort(products, comparator);
//    }

    public void sortByHeight() {
        Comparator<Product> comparator = new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if (p1.getPrice() < p2.getPrice()) {
                return 1;
            } else if (p1.getPrice() > p2.getPrice()) {
                return -1;
            }
            return 0;
            }
        };
        products.sort(comparator);
    }

    public void sortByLow() {
        Comparator<Product> comparator = new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                if (p1.getPrice() > p2.getPrice()) {
                    return 1;
                } else if (p1.getPrice() < p2.getPrice()) {
                    return -1;
                }
                return 0;
            }
        };
        products.sort(comparator);
    }

        public String toCsv () {
            String result = "";
            for (Product product : this.products) {
                result += product.toCsv() + "\n";
            }
            return result;
        }
    }
