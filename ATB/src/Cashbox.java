import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;


public class Cashbox {
    private Map<BigDecimal, Integer> bankNotesContained;
    public BigDecimal customerMoney;
    public BigDecimal goodPrice;
    public BigDecimal oddMoney;

    public Cashbox(Map<BigDecimal, Integer> bankNotesToContain)
    {
        this.bankNotesContained = new TreeMap<>(Collections.reverseOrder());
        copyBankNotesInfo(bankNotesToContain, bankNotesContained);
        printBanknoteInfo(bankNotesToContain, "Inital cashbox state:");
    }

    private Map<BigDecimal, Integer> copyBankNotesInfo(Map<BigDecimal, Integer> fromContainer, Map<BigDecimal, Integer> toContainer)
    {
        fromContainer.entrySet().forEach((e) -> addBankNoteIntoContainer(toContainer, e.getKey(), e.getValue()));
        return this.bankNotesContained;
    }

    private void addBankNoteIntoContainer(Map<BigDecimal, Integer> container, BigDecimal denomination, Integer amount)
    {
        container.put(denomination, amount);
    }

    public boolean setCurrentTransactionCashInfo(String cash, String price)
    {
        if(!validateCurrentTransactionCashInfo(cash, price))
        {
            return false;
        }
        calculateOddMoney();
        return true;
    }

    private boolean validateCurrentTransactionCashInfo(String cash, String price)
    {
        try {
            customerMoney = new BigDecimal(cash).round(new MathContext(4));
            goodPrice = new BigDecimal(price).round(new MathContext(4));
        } catch (NumberFormatException ex) {
            System.out.println("##### Invalid cash data. Can't process #####\n");
            return false;
        }

        return true;
    }

    private Map<BigDecimal, Integer> addIncomingBankNotesToCashbox()
    {
        BigDecimal currentBiggestBanknote;
        int amountOfBanknotes;
        BigDecimal tempIncomingCash = new BigDecimal(customerMoney.toString());
        Map<BigDecimal, Integer> bankNotesAdded = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<BigDecimal, Integer> entry: bankNotesContained.entrySet())
        {
            amountOfBanknotes = tempIncomingCash.divide(entry.getKey()).intValue();
            if (amountOfBanknotes >= 1)
            {
                currentBiggestBanknote = entry.getKey();
                tempIncomingCash = tempIncomingCash.subtract(currentBiggestBanknote.multiply(new BigDecimal(Integer.toString(amountOfBanknotes))));
                addBankNoteIntoContainer(bankNotesContained, currentBiggestBanknote, bankNotesContained.get(currentBiggestBanknote) + amountOfBanknotes);
                addBankNoteIntoContainer(bankNotesAdded, currentBiggestBanknote, amountOfBanknotes);
            }

            if (tempIncomingCash.compareTo(new BigDecimal("0")) == 0)
            {
                break;
            }
        }

        return bankNotesAdded;
    }

    private BigDecimal calculateOddMoney()
    {
        oddMoney = customerMoney.subtract(goodPrice);
        return oddMoney;
    }

    private boolean isPriceValid()
    {
        if (goodPrice != null && goodPrice.compareTo(new BigDecimal("0")) <= 0)
        {
            System.out.println("##### Invalid price given #####\n");
            return false;
        }
        return true;
    }

    public boolean validatePossibilityToGiveOddMoney()
    {
        if (!isGivenMoneyValid() ||
                !isPriceValid() ||
                !isCashboxBiggerThanOddMoneyToGive() ||
                !isGivenCashIsEnoughToPay() ||
                !tryToGiveOddMoney()
        )
        {
            printBanknoteInfo(bankNotesContained, "Banknotes left:");
            return false;
        }

        printBanknoteInfo(bankNotesContained, "Banknotes left:");

        return true;
    }

    private boolean isGivenMoneyValid()
    {
        if (customerMoney != null && customerMoney.compareTo(new BigDecimal("0")) < 0)
        {
            System.out.println("##### Invalid customer cash info #####\n");
            return false;
        }
        return true;
    }

    private void printBanknoteInfo(Map<BigDecimal, Integer> bankNotes, String message)
    {
        System.out.println(message);
        if (bankNotes.isEmpty())
        {
            System.out.println("0 results\n");
            return;
        }

        for (Map.Entry<BigDecimal, Integer> entry: bankNotes.entrySet()) {
            System.out.printf("%f: %d%n", entry.getKey(), entry.getValue());
        }
        System.out.println();
    }

    private boolean isCashboxBiggerThanOddMoneyToGive()
    {
        BigDecimal cashboxSumOfMoney = new BigDecimal("0");
        for (Map.Entry<BigDecimal, Integer> entry: bankNotesContained.entrySet()) {
            cashboxSumOfMoney = cashboxSumOfMoney.add(entry.getKey().multiply(new BigDecimal(Integer.toString(entry.getValue()))));
        }

        if (cashboxSumOfMoney.compareTo(oddMoney) < 0)
        {
            System.out.println("##### There is not enough money in the cashbox #####\n");
            return false;
        }
        return true;
    }

    private boolean isGivenCashIsEnoughToPay()
    {
        if (oddMoney.compareTo(new BigDecimal("0")) < 0)
        {
            System.out.println("##### Not enough cash given! #####\n");
            return false;
        }
        return true;
    }

    private boolean tryToGiveOddMoney()
    {
        BigDecimal currentBiggestBanknote;
        int amountOfBanknotes;
        BigDecimal oddMoney = new BigDecimal(this.oddMoney.toString());
        Map<BigDecimal, Integer> tempBankNotes = new TreeMap<>(Comparator.reverseOrder());
        bankNotesContained.keySet().forEach((k) -> tempBankNotes.put(k, bankNotesContained.get(k)));

        Map<BigDecimal, Integer> oddMoneyInBankNotes = new TreeMap<>(Collections.reverseOrder());

        for (Map.Entry<BigDecimal, Integer> entry: tempBankNotes.entrySet()) {
            amountOfBanknotes = oddMoney.divide(entry.getKey()).intValue();
            if (amountOfBanknotes >= 1)
            {
                if (tempBankNotes.get(entry.getKey()) >= amountOfBanknotes)
                {
                    currentBiggestBanknote = entry.getKey();
                    oddMoneyInBankNotes.put(currentBiggestBanknote, amountOfBanknotes);
                    oddMoney = oddMoney.subtract(currentBiggestBanknote.multiply(new BigDecimal(Integer.toString(amountOfBanknotes))));
                    tempBankNotes.put(entry.getKey(), tempBankNotes.get(entry.getKey()) - amountOfBanknotes);
                }
            }
        }

        if(oddMoney.compareTo(new BigDecimal("0")) != 0)
        {
            System.out.println("##### Could not give odd money #####\n");
            return false;
        }

        bankNotesContained = tempBankNotes;
        printBanknoteInfo(addIncomingBankNotesToCashbox(), "Bank notes accepted:");
        printBanknoteInfo(oddMoneyInBankNotes, "Bank notes given out:");
        return true;
    }
}
