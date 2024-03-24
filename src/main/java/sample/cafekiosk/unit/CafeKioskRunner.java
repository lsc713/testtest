package sample.cafekiosk.unit;

import sample.cafekiosk.unit.beverages.Americano;
import sample.cafekiosk.unit.beverages.Latte;

public class CafeKioskRunner {
    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        System.out.println(">>> add americano");


        cafeKiosk.add(new Latte());
        System.out.println(">>> add Latte");


        int totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("total price : " + totalPrice);
    }
}
