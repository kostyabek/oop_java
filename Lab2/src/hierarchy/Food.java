package hierarchy;

// #45

import java.util.Date;

public class Food extends Good{
    Date bestBefore;
    FoodGroup foodGroup;

    public Food(String name, String vendor, Date dateOfProduction, double price, Date bestBefore, FoodGroup foodGroup) {
        super(name, vendor, dateOfProduction, price);
        this.bestBefore = bestBefore;
        this.foodGroup = foodGroup;
    }

    @Override
    public void placeToStore() {
        if (foodGroup == FoodGroup.DAIRY
                || foodGroup == FoodGroup.BEVERAGE
                || foodGroup == FoodGroup.MEAT) {
            System.out.println("I gotta store it in the fridge!");
        }
        else
            System.out.println("I gotta store it on the stall!");
    }
}