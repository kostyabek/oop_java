package source;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Character[] initialValues = {'a','1','2','b','c','3','4','d','e','5','6','7','f','g','h','i','8','9','1','2','3','j'};
        Main mn = new Main();

        System.out.println(mn.getBiggestChain(initialValues));
    }

    public int getBiggestChain(Character[] values) {
        if (isSingleType(values) || values.length == 0)
            return 0;

        List<Chain> chains = new ArrayList<>();
        int offset = 0;

        for (int i = 0; i < values.length; i++) {
            if (Character.isDigit(values[i]) || Character.isLetter(values[i])) {
                chains.add(new Chain());
            }
            Chain currentChain = chains.get(chains.size() - 1);
            offset = 0;

            do {
                if (i + offset > values.length - 1)
                    break;
                if (Character.isDigit(values[i + offset]))
                    offset = insertDigits(currentChain, values, i + offset);
                else
                    offset = insertLetters(currentChain, values, i + offset);
            } while(currentChain.letters.size() == 0 || currentChain.digits.size() == 0);

        }

        for (Chain chain: chains) {
            chain.printDigits();
            chain.printLetters();
            System.out.println();
        }

        int maxChainSize = 0;
        int minSubChainSize = 0;
        for (Chain chain: chains) {
            minSubChainSize = (chain.digits.size() <= chain.letters.size()) ? chain.digits.size() : chain.letters.size();
            if (minSubChainSize > maxChainSize) {
                maxChainSize = minSubChainSize;
            }
        }
        return maxChainSize;
    }

    public int insertDigits(Chain currentChain, Character[] values, int index) {
        for (int j = index; j < values.length && Character.isDigit(values[j]); j++) {
            currentChain.digits.add(values[j]);
        }
        return currentChain.digits.size();
    }

    public int insertLetters(Chain currentChain, Character[] values, int index) {
        for (int j = index; j < values.length && Character.isLetter(values[j]); j++) {
            currentChain.letters.add(values[j]);
        }
        return currentChain.letters.size();
    }

    public Boolean isSingleType(Character[] values) {
        Boolean hasDigits = false;
        Boolean hasLetters = false;

        for (Character item: values) {
            if (Character.isLetter(item))
                hasLetters = true;
            else if (Character.isDigit(item))
                hasDigits = true;
        }

        if (!hasDigits || !hasLetters)
            return true;
        return false;
    }
}
