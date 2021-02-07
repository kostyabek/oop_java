package hierarchy;

// #45

import java.util.Date;

public class Good {
    String name;
    String vendor;
    Date dateOfProduction;
    double price;

    public Good() {}

    public Good(String name, String vendor, Date dateOfProduction, double price) {
        this.name = name;
        this.vendor = vendor;
        this.dateOfProduction = dateOfProduction;
        this.price = price;
    }

    public void placeToStore() {
        System.out.println("I don't know how to store it!");
    }

    public void sell(Good good) {
        System.out.println(String.format("%s has been traded for %s", this.name, good.name));
    }

    public void sell(Currency currency) {
        System.out.println(String.format("%s has been sold for %.2f %s", this.name, price * currency.rate, currency));
    }
}
