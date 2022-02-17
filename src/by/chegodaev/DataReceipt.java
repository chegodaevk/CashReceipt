package by.chegodaev;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataReceipt {

    ProductAndCardData productAndCardData = new ProductAndCardData();
    private double sum = 0.0;
    private int id_card = 0;
    private int id_product = 0;
    private int quantity = 0;

    public void getReceiptData(String[] array){
        getHeaderReceipt();
        for(int i=0; i<array.length; i++){
            String str = array[i];
            String str_id = str.substring(0,str.indexOf("-"));
            String str_quantity = str.substring(str.indexOf("-")+1);
            if (!str_id.equals("card")){
                id_product = stringToInteger(str_id);
                quantity = stringToInteger(str_quantity);
                double price = Double.parseDouble(productAndCardData.productMap.get(id_product)[1]);
                if(quantity <= 5) {
                    System.out.printf(" %-2s %-12s %8s %8s \n", quantity,productAndCardData.productMap.get(id_product)[0],
                            "$" + price, "$" + price * quantity);
                    sum = sum + (price * quantity);
                } else {
                    double priceWithPromo = Math.round((price - price / 10) * 1e2) / 1e2;
                    System.out.printf(" %-2s %-12s %8s \n %-2s %-12s %8s %8s \n", quantity, productAndCardData.productMap.get(id_product)[0],
                            "$" + price, "", "action -10%", "$" + priceWithPromo, "$" + Math.round(priceWithPromo * quantity * 1e2) / 1e2);

                    sum = sum + (Math.round((price * quantity - price * quantity / 10) * 1e2) / 1e2);

                }
            } else {
                id_card = stringToInteger(str_quantity);
            }
        }
        getFooterReception();
    }

    private Integer stringToInteger(String str){
        int id = 0;
        try {
            id = Integer.parseInt(str);
        } catch (NumberFormatException e){
            System.err.println("Неправильный формат строки");
        }
        return id;
    }

    private void getHeaderReceipt (){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        System.out.printf("%23s \n", "CASH RECEIPT");
        System.out.printf("%25s \n", "supermarket 24/7");
        System.out.printf("%30s \n", "152, Independence, StarWars");
        System.out.printf("%29s \n", "phone: +375(44) 753 44 99");
        System.out.printf("%-17s %-14s \n", "CASHIER №" + (int)(Math.random()*10000),"DATE: " + dateFormat.format(new Date()));
        System.out.printf("%17s %-14s \n", "", "TIME: " + timeFormat.format(new Date()));
        System.out.printf("%-3s %-12s %8s %8s \n","QTI","DESCRIPTION", "PRICE", "TOTAL");
        System.out.println("----------------------------------");
    }

    private void getFooterReception(){
        System.out.println("==================================");
        System.out.printf("%-20s %13s \n","Total amount:","$" + Math.round(sum * 1e2)/1e2);
        if(id_card != 0) {
            System.out.printf("%-20s %13s \n", "Discount:", productAndCardData.discountMap.get(id_card) + "%");
            double total_cost = sum - (double) sum * (productAndCardData.discountMap.get(id_card)) / 100;
            System.out.printf("%-20s %13s", "Total cost:", "$" + Math.round(total_cost * 1e2) / 1e2);
        }else {
            System.out.printf("%-20s %13s", "Total cost:", "$" + sum);
        }
    }
}
