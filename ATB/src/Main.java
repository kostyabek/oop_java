import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] amounts = new int[]{1, 2, 1, 0, 3, 5, 10, 11, 2, 0, 13, 4};
        Map<BigDecimal, Integer> bankNotes = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i < Denomination.values().length; i++) {
            bankNotes.put(new BigDecimal(Double.toString(Denomination.values()[i].denomination)), amounts[i]);
        }

        Cashbox cashbox = new Cashbox(bankNotes);

        if (!cashbox.setCurrentTransactionCashInfo("100", "-100.36") || !cashbox.validatePossibilityToGiveOddMoney())
        {
            System.out.println("Transaction failed.");
        } else
        {
            System.out.println("Transaction successful!");
        }
    }
}
