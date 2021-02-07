package hierarchy;

// #45

import java.util.Date;

public class RadioElectronics extends Good {
    ElectronicsType electronicsType;
    Date warranty;

    public RadioElectronics(String name, String vendor, Date dateOfProduction, double price, ElectronicsType electronicsType, Date warranty) {
        super(name, vendor, dateOfProduction, price);
        this.electronicsType = electronicsType;
        this.warranty = warranty;
    }

    @Override
    public void placeToStore() {
        System.out.println("I gotta store it in a dry place!");
    }
}
