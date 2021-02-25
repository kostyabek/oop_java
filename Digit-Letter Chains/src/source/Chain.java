package source;

import java.util.ArrayList;
import java.util.List;

public class Chain {
    public List<Character> digits = new ArrayList<>();
    public List<Character> letters = new ArrayList<>();

    public void printDigits() {
        System.out.print("Digits:");
        for (Character digit: digits) {
            System.out.print(String.format(" %c", digit));
        }
        System.out.println();
    }

    public void printLetters() {
        System.out.print("Letters:");
        for (Character letter: letters) {
            System.out.print(String.format(" %c", letter));
        }
        System.out.println();
    }
}