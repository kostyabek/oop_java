package main;

import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {
        Main mn = new Main();
        System.out.println("Reverse int:");
        System.out.println(mn.reverseIntArr(12345));
        System.out.println();
        System.out.println("Reverse int(manual):");
        System.out.println(mn.reverseIntArrNew(-199459999));

        System.out.println("Concatenate int arrays (prime numbers only):");
        int[] arr1 = mn.concatIntArr(new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 10}, new int[]{11, 12, 13, 14, 15});

        for (int i = 0; i < arr1.length; i++)
            System.out.println(arr1[i]);
        System.out.println();

        System.out.println("Insertion sort:");
        int[] arr2 = new int[]{5, 4, 9, 7, 11, 1, 3, 2};
        mn.insertionSort(arr2);
        for (int i = 0; i < arr2.length; i++)
            System.out.println(arr2[i]);
        System.out.println();

        System.out.println("Decimal to Octal:");
        System.out.println(mn.decimalToOctal(112));

        System.out.println("Decimal to Hexadecimal");
        System.out.println(mn.decimalToHex(12345678));
    }

    //Створіть метод, який може перевернути будь яку число int. Приклад - 357 на вході, метод поверне 753
    public int reverseIntArr (int num)
    {
        return Integer.parseInt(new StringBuilder(Integer.toString(num)).reverse().toString());
    }
    //Manual implementation
    public int reverseIntArrNew (int num) {
        int len = 0;
        int temp = num;
        int res = 0;

        while (temp != 0) {
            temp /= 10;
            len++;
        }
        int[] numbers = new int[len];
        for (int i = 0; i < len; i++) {
            if (res == Integer.MAX_VALUE || res == Integer.MIN_VALUE) {
                System.out.println("Passed value is too large!");
                return res;
            }
            numbers[i] = num % 10;
            num /= 10;
            res += numbers[i] * Math.pow(10, len - 1 - i);
        }
        return res;
    }

    //Створіть метод, який дозволяє поєднати між собою будь яку кількість масивів int[] та повернути результуючий масив, який буде складатися виключно з простих чисел, які були у складі масивів.
    public int[] concatIntArr(int[] ... arr)
    {
        if (arr.length == 0)
            throw new IllegalArgumentException();

        for (int i = 0; i < arr.length; i++)
            arr[i] = Arrays.stream(arr[i]).filter(num -> isPrime(num)).toArray();

        int len = 0;
        for (int i = 0; i < arr.length; i++)
            len += arr[i].length;

        int[] res = new int[len];
        len = 0;
        for (int i = 0; i < arr.length; i++)
        {
            System.arraycopy(arr[i], 0, res, len, arr[i].length);
            len += arr[i].length;
        }
        return res;
    }
    private boolean isPrime(int num)
    {
        if (num == 1 || num == 2 || num == 3)
            return true;
        for (int i = 2; i <= num/2; i++)
            if (num % i == 0)
                return false;
        return true;
    }

    //Створіть метод, який сортує будь який масив int[] методом вставок.
    //З функціональною декомпозицією
    public void insertionSort(int[] arr)
    {
        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException();

        int current, j;
        for (int i = 1; i < arr.length; i++)
        {
            current = getCurrentElement(arr, i);
            j = i - 1;
            j = findPosition(arr, current, j);
            setCurrentIntoPosition(arr, current, j);
        }
    }

    public int getCurrentElement(int[] arr, int i) {
        return arr[i];
    }

    public int findPosition(int[] arr, int current, int j) {
        while (j >= 0 && arr[j] > current)
        {
            swap(arr, j);
            j--;
        }
        return j;
    }

    public void swap(int[] arr, int j) {
        arr[j + 1] = arr[j];
    }
    public void setCurrentIntoPosition(int[] arr, int current, int j) {
        arr[j + 1] = current;
    }

    //Створіть метод, який приймає параметр int та конвертує його з десятичної у восьмирічну систему числення та повертає у вигляді String. Приклад- приймає 9, повертає =011=
    public String decimalToOctal(int num)
    {
        StringBuilder res = new StringBuilder("=");
        while(num != 0)
        {
            res.append(num % 8);
            num /= 8;
        }
        res.append("0=");
        return res.reverse().toString();
    }

    //Створіть метод, який приймає параметр int та конвертує його з десятичної у шістнадцятирічну систему обчислення та повертає у вигляді String. Приклад- приймає 11, повертає =0xb=
    public String decimalToHex(int num)
    {
        StringBuilder res = new StringBuilder("=");
        char alphabet[] = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        while(num != 0)
        {
            if (num % 16 > 9)
                res.append(alphabet[(num % 16) - 1]);
            else
                res.append(num % 16);
            num /= 16;
        }
        res.append("x0=");
        return res.reverse().toString();
    }
}
