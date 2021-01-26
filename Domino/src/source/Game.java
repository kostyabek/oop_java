package source;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    ArrayList<Player> players;
    ArrayList<Stone> bank;
    Player currentPlayer;
    ArrayDeque<Stone> table;
    Stone[] sockets = new Stone[2];
    int handSize;
    int turnCounter = 0;

    Scanner sc = new Scanner(System.in);

    public Game() {
        initPlayers();
        initBank();
        giveStones();
        currentPlayer = pickFirstPlayer();
        table = new ArrayDeque<>();
    }

    public void gameloop() {
        startGame();

        turnCounter++;

        System.out.println(String.format("It is %s's turn to play!", currentPlayer.name));
        currentPlayer.printHand();
        currentPlayer.makeFirstTurn(sc, table);
        currentPlayer.endTurn(sc);

        if (players.indexOf(currentPlayer) + 1 == players.size())
            currentPlayer = players.get(0);
        else
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);

        while (true) {
            if (checkIfWinner())
                return;

            System.out.println(String.format("It is %s's turn to play!", currentPlayer.name));
            printTable();
            getSockets();
            printSockets();

            currentPlayer.makeTurn(sc, table, sockets, bank, handSize);
            currentPlayer.endTurn(sc);

            if (players.indexOf(currentPlayer) + 1 == players.size())
                currentPlayer = players.get(0);
            else
                currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }
    }

    private boolean checkIfWinner() {
        if (currentPlayer.getHand().isEmpty()) {
            System.out.println(String.format("%s is a winner!", currentPlayer.name));
            return true;
        }
        return false;
    }

    private void startGame() {
        String keyWord;
        System.out.println(String.format("%s is playing first!", currentPlayer.name));

        while (true) {
            System.out.print("Type 'start' to start the game: ");
            keyWord = sc.nextLine();
            if (keyWord.contentEquals("start"))
                break;
        }
    }

    private void printSockets() {
        System.out.println("-----------SOCKETS-----------");
        if (sockets[0].equals(sockets[1])) {
            System.out.println(String.format("%d %d", sockets[0].getLeftValue(), sockets[0].getRightValue()));
            return;
        }

        for (Stone stone: sockets) {
            if (stone.leftIsFree)
                System.out.print(stone.getLeftValue() + " ");
            if (stone.rightIsFree)
                System.out.print(stone.getRightValue() + " ");
        }
        System.out.println();
    }

    private void printTable() {
        System.out.println("------------TABLE------------");
        for (Stone stone: table) {
            System.out.print(String.format("|%d| ", stone.getLeftValue()));
        }
        System.out.println();

        for (Stone stone: table) {
            System.out.print(String.format("|%d| ", stone.getRightValue()));
        }
        System.out.println();
    }

    private void getSockets() {
        sockets[0] = table.getFirst();
        sockets[1] = table.getLast();
    }

    private Player pickFirstPlayer() {
        int min = 10;
        Player first = null;

        for (Player player: players) {
            for (Stone stone: player.getHand()) {
                if (stone.getLeftValue() == stone.getRightValue()
                        && stone.getLeftValue() != 0
                        && stone.getLeftValue() < min) {
                    min = stone.getLeftValue();
                    first = player;
                }
            }
        }

        if (first == null) {
            Random rnd = new Random();
            first = players.get(rnd.nextInt(players.size()));
        }
        return first;
    }

    private void giveStones() {
        int amount = (players.size() == 2) ? 7 : 5;

        for (Player player: players) {
            bank.stream().limit(amount).forEach(stone -> player.getHand().add(stone));
            bank.subList(0, amount).clear();
        }
    }

    private void initBank() {
        bank = new ArrayList<Stone>();
        for (int i = 0; i < 7; i++)
            for (int j = i; j < 7; j++)
                bank.add(new Stone(i, j));
        Collections.shuffle(bank);
    }

    private void initPlayers() {
        String playerName;
        String numOfPlayersStr;
        players = new ArrayList<Player>();
        int numOfPlayers;
        Pattern pattern = Pattern.compile("[2-4]");
        Matcher matcher;

        do {
            System.out.print("Enter the number of players: ");
            numOfPlayersStr = sc.nextLine();
            matcher = pattern.matcher(numOfPlayersStr);
        } while (!matcher.matches());

        numOfPlayers = Integer.parseInt(numOfPlayersStr);
        if (numOfPlayers == 2)
            handSize = 7;
        else
            handSize = 5;

        for (int i = 0; i < numOfPlayers; i++) {
            System.out.println(String.format("Enter player %d name: ", i + 1));
            playerName = sc.nextLine();
            players.add(new Player(playerName));
        }
    }
}
