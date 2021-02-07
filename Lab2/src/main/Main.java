package main;

import access.Access;
import hierarchy.Currency;
import hierarchy.Food;
import hierarchy.FoodGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Main mn = new Main();

    // #31
        int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
        IFilterArray ifa = new IFilterArray() {

            @Override
            public boolean foo(int num) {
                return num < 4;
            }
        };
        mn.filterArray(arr, ifa);

    // #34
        System.out.println(mn.getDays(Months.OCTOBER));

    // #41 Створіть код, у якому генеруються слідуючі типи виключень: ArrayIndexOutOfBoundsException, ArithmeticException, NullPointerException, IndexOutOfBoundsException, Exception. Продемонструйте відмінність між =checked= та =unchecked= виключеннями.
        int[] mass = new int[]{1, 2, 3};
        try {
            mass[3] = 4;
        } catch(ArrayIndexOutOfBoundsException ex) {
            mass[2] = 4;
            System.out.println("1) " + ex.getMessage());
        }

        int a;
        try {
            a = 5 / 0;
        } catch(ArithmeticException ex) {
            a = 0;
            System.out.println("2) " + ex.getMessage());
        }

        Main main = null;
        try {
            main.toString();
        } catch(NullPointerException ex) {
            System.out.println("3) " + ex.getMessage());
        }

        ArrayList<Integer> arrLi = new ArrayList<>();
        try {
            System.out.println(arrLi.get(0));
        } catch(IndexOutOfBoundsException ex) {
            System.out.println("4) " + ex.getMessage());
        }

        Random rnd = new Random();
        try {
            if (rnd.nextBoolean())
                throw new Exception("Just the instance of the Exception class");
            else
                System.out.println("Well, no exception this time..");
        } catch(Exception ex) {
            System.out.println("5) " + ex.getMessage());
        }

        mn.simpleFunc(1, 1);
        try {
            mn.getFileInfo(new File("data_file.txt"));
        } catch(FileNotFoundException ex) {
            System.out.println("checked exception: " + ex.getMessage());
        }

    // #45
        Food bread = new Food("Voskresenskiy", "Mykolaiv Hlib", new Date(10000000000L), 4.75, new Date(10003000000L), FoodGroup.GROCERY);
        bread.sell(Currency.GBP);

    // #49
        Access acs = new Access();
        System.out.println(acs.pubVar);
    }

    // #31 Створіть метод який дозволяє фільтрувати елементи будь якого масиву int та виводити ці елементи на консоль. Умови фільтрації повинні передаватися в метод як параметр. Використовуйте функціональні інтерфейси та анонимні класи.
    public void filterArray(int[] arr, IFilterArray ifa) {
        int[] inds = new int[arr.length];
        for (int i = 0; i < inds.length; i++) {
            inds[i] = -1;
        }

        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (ifa.foo(arr[i])) {
                System.out.println(arr[i]);
            }
        }
    }

    // #34 Создайте метод, который принимает параметр- значение перечисления для месяца, а возвращает кол-во дней в месяце. Февраль всегда -28
    public int getDays(Months month) {
        return month.numOfDays;
    }

    // #41 unchecked vs checked
    public double simpleFunc(double x, double y) throws IllegalArgumentException {
        if (x == 0 || y == 0)
            throw new IllegalArgumentException();
        else
            return (Math.pow(x, 4) + 14) / (x * y);
    }

    public void getFileInfo(File file) throws FileNotFoundException {
        if (!file.exists())
            throw new FileNotFoundException("No such file as " + file.getAbsolutePath());
        else {
            System.out.println(file.getAbsolutePath());
            System.out.println(file.canRead());
            System.out.println(file.canWrite());
            System.out.println(file.length());
        }

    }
}
