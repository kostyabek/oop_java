package source;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    String name;
    private ArrayList<Stone> hand = new ArrayList<Stone>();

    public Player(String name) {
        this.name = name;
    }

    public ArrayList<Stone> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", stones=" + hand +
                '}';
    }

    public void printHand() {
        System.out.println("----------YOUR HAND----------");
        for (Stone stone: hand) {
            System.out.print(String.format("|%d| ", stone.getLeftValue()));
        }
        System.out.println();

        for (Stone stone: hand) {
            System.out.print(String.format("|%d| ", stone.getRightValue()));
        }
        System.out.println();
    }

    public void makeFirstTurn(Scanner sc, ArrayDeque<Stone> table) {
        int stoneInd;

        do {
            System.out.print("Choose a stone: ");
            stoneInd = sc.nextInt();
        } while (stoneInd > hand.size() || stoneInd < 1);
        stoneInd--;
        table.addFirst(hand.get(stoneInd));
        hand.remove(stoneInd);
    }

    public void makeTurn(Scanner sc, ArrayDeque<Stone> table, Stone[] sockets, ArrayList<Stone> bank, int handSize) {
        int stoneInd;
        int left;
        int right;
        Stone socket1 = sockets[0];
        Stone socket2 = sockets[1];

        /*if (bank.isEmpty()) {
            System.out.println("Bank is empty!");
            if (!handChecker(socket1, socket2)) {
                System.out.println("You cannot place a stone this turn!");
                return;
            }
        } else {*/
        if (bank.isEmpty())
            System.out.println("Bank is empty!");
        while (!handChecker(socket1, socket2, handSize) && !bank.isEmpty()) {
            hand.add(bank.get(0));
            bank.remove(0);
        }
        printHand();
        if (bank.isEmpty() && !handChecker(socket1, socket2)){
            System.out.println("Bank is empty!");
            System.out.println("You cannot place a stone this turn!");
            return;
        }
       //}

        do {
            System.out.print("Choose a stone: ");
            stoneInd = sc.nextInt();
            while (stoneInd > hand.size() || stoneInd < 1) {
                System.out.print("Choose a stone: ");
                stoneInd = sc.nextInt();
            }

            stoneInd--;
            Stone chosenStone = hand.get(stoneInd);
            left = chosenStone.getLeftValue();
            right = chosenStone.getRightValue();

            if (left == socket1.getLeftValue() && socket1.leftIsFree) {
                chosenStone.leftIsFree = false;
                socket1.leftIsFree = false;
                table.addFirst(chosenStone);
                hand.remove(chosenStone);
                break;
            } else if (left == socket1.getRightValue() && socket1.rightIsFree) {
                chosenStone.leftIsFree = false;
                socket1.rightIsFree = false;
                table.addFirst(chosenStone);
                hand.remove(chosenStone);
                break;
            } else if (left == socket2.getLeftValue() && socket2.leftIsFree) {
                chosenStone.leftIsFree = false;
                socket2.leftIsFree = false;
                table.addLast(chosenStone);
                hand.remove(chosenStone);
                break;
            } else if (left == socket2.getRightValue() && socket2.rightIsFree) {
                chosenStone.leftIsFree = false;
                socket2.rightIsFree = false;
                table.addLast(chosenStone);
                hand.remove(chosenStone);
                break;
            } else if (right == socket1.getLeftValue() && socket1.leftIsFree) {
                chosenStone.rightIsFree = false;
                socket1.leftIsFree = false;
                table.addFirst(chosenStone);
                hand.remove(chosenStone);
                break;
            } else if (right == socket1.getRightValue() && socket1.rightIsFree) {
                chosenStone.rightIsFree = false;
                socket1.rightIsFree = false;
                table.addFirst(chosenStone);
                hand.remove(chosenStone);
                break;
            } else if (right == socket2.getLeftValue() && socket2.leftIsFree) {
                chosenStone.rightIsFree = false;
                socket2.leftIsFree = false;
                table.addLast(chosenStone);
                hand.remove(chosenStone);
                break;
            } else if (right == socket2.getRightValue() && socket2.rightIsFree) {
                chosenStone.rightIsFree = false;
                socket2.rightIsFree = false;
                table.addLast(chosenStone);
                hand.remove(chosenStone);
                break;
            }
        } while (true);
    }

    public void endTurn(Scanner sc) {
        String keyWord;
        // TODO: 1/27/2021 Fix excess '\n' request when no input before calling
        if (sc.hasNextLine())
            sc.nextLine();
        while (true) {
            System.out.print("Type 'next' to end your turn: ");
            keyWord = sc.nextLine();
            if (keyWord.contentEquals("next")) {
                System.out.println();
                break;
            }
        }
    }

    private boolean handChecker(Stone socket1, Stone socket2, int handSize) {
        if (hand.size() < handSize)
            return false;
        for (Stone stone: hand) {
            if (stone.getLeftValue() == socket1.getLeftValue() && socket1.leftIsFree || stone.getLeftValue() == socket1.getRightValue() && socket1.rightIsFree) {
                return true;
            } else if (stone.getLeftValue() == socket2.getLeftValue() && socket2.leftIsFree || stone.getLeftValue() == socket2.getRightValue() && socket2.rightIsFree) {
                return true;
            } else if (stone.getRightValue() == socket1.getLeftValue() && socket1.leftIsFree || stone.getRightValue() == socket1.getRightValue() && socket1.rightIsFree) {
                return true;
            } else if (stone.getRightValue() == socket2.getLeftValue() && socket2.leftIsFree || stone.getRightValue() == socket2.getRightValue() && socket2.rightIsFree) {
                return true;
            }
        }
        return false;
    }
    public boolean handChecker(Stone socket1, Stone socket2) {
        for (Stone stone: hand) {
            if (stone.getLeftValue() == socket1.getLeftValue() && socket1.leftIsFree || stone.getLeftValue() == socket1.getRightValue() && socket1.rightIsFree) {
                return true;
            } else if (stone.getLeftValue() == socket2.getLeftValue() && socket2.leftIsFree || stone.getLeftValue() == socket2.getRightValue() && socket2.rightIsFree) {
                return true;
            } else if (stone.getRightValue() == socket1.getLeftValue() && socket1.leftIsFree || stone.getRightValue() == socket1.getRightValue() && socket1.rightIsFree) {
                return true;
            } else if (stone.getRightValue() == socket2.getLeftValue() && socket2.leftIsFree || stone.getRightValue() == socket2.getRightValue() && socket2.rightIsFree) {
                return true;
            }
        }
        return false;
    }
}