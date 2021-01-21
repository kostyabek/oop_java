package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Reverse int:");
        System.out.println(reverseIntArr(12345));
        System.out.println();

        System.out.println("Concatenate int arrays (prime numbers only):");
        int[] arr1 = concatIntArr(new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 10}, new int[]{11, 12, 13, 14, 15});
        for (int i = 0; i < arr1.length; i++)
            System.out.println(arr1[i]);
        System.out.println();

        System.out.println("Insertion sort:");
        int[] arr2 = new int[]{5, 4, 9, 7, 11, 1, 3, 2};
        insertionSort(arr2);
        for (int i = 0; i < arr2.length; i++)
            System.out.println(arr2[i]);
        System.out.println();

        System.out.println("Decimal to Octal:");
        System.out.println(decimalToOctal(112));

        System.out.println("Decimal to Hexadecimal");
        System.out.println(decimalToHex(12345678));
    }

    //Створіть метод, який може перевернути будь яку число int. Приклад - 357 на вході, метод поверне 753
    public static int reverseIntArr(int num)
    {
        return Integer.parseInt(new StringBuilder(Integer.toString(num)).reverse().toString());
    }

    //Створіть метод, який дозволяє поєднати між собою будь яку кількість масивів int[] та повернути результуючий масив, який буде складатися виключно з простих чисел, які були у складі масивів.
    public static int[] concatIntArr(int[] ... arr)
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
    private static boolean isPrime(int num)
    {
        if (num == 1 || num == 2 || num == 3)
            return true;
        for (int i = 2; i <= num/2; i++)
            if (num % i == 0)
                return false;
        return true;
    }

    //Створіть метод, який сортує будь який масив int[] методом вставок.
    public static void insertionSort(int[] arr)
    {
        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException();

        int curr, j;
        for (int i = 1; i < arr.length; i++)
        {
            curr = arr[i];
            j = i - 1;
            while (j >= 0 && arr[j] > curr)
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = curr;
        }
    }

    //Створіть метод, який приймає параметр int та конвертує його з десятичної у восьмирічну систему числення та повертає у вигляді String. Приклад- приймає 9, повертає =011=
    public static String decimalToOctal(int num)
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
    public static String decimalToHex(int num)
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
