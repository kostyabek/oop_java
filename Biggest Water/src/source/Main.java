package source;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main mn = new Main();
        int[][] initialValues = new int[][]{
                new int[]{4,2,3,0,5,0,0,0,1,0},
                new int[]{1,1,1,1,0,1,3,4,0,1},
                new int[]{1,0,0,0,5,6,0,4,6,0},
                new int[]{1,0,3,2,0,5,0,0,1,1},
        };

        List<List<ZeroCoordinates>> passedZeroes = new ArrayList<>();

        for (int i = 0; i < initialValues.length; i++) {
            for (int j = 0; j < initialValues[0].length; j++) {
                zero:
                if (initialValues[i][j] == 0) {
                    for (List<ZeroCoordinates> chain: passedZeroes) {
                        for (ZeroCoordinates coords: chain) {
                            if (coords.i == i && coords.j == j) {
                                break zero;
                            }
                        }
                    }
                    passedZeroes.add(new ArrayList<>());
                    passedZeroes.get(passedZeroes.size() - 1).add(new ZeroCoordinates(i, j));
                    mn.dive(initialValues, i, j, passedZeroes);
                }
            }
        }
        int maxSize = 0;
        for (List<ZeroCoordinates> chain: passedZeroes) {
            if (chain.size() > maxSize) {
                maxSize = chain.size();
            }
        }
        System.out.println(String.format("Maximum chain size is %d", maxSize));
    }

    public void dive(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        System.out.println(String.format("i=%d j=%d", i, j));

        //WEST
        diveWest(arr, i, j, zeroes);
        //NORTH-WEST
        diveNorthWest(arr, i, j, zeroes);
        //NORTH
        diveNorth(arr, i, j, zeroes);
        //NORTH-EAST
        diveNorthEast(arr, i, j, zeroes);
        //EAST
        diveEast(arr, i, j, zeroes);
        //SOUTH-EAST
        diveSouthEast(arr, i, j, zeroes);
        //SOUTH
        diveSouth(arr, i, j, zeroes);
        //SOUTH-WEST
        diveSouthWest(arr, i, j, zeroes);
    }

    public void diveWest(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (j-1 >= 0) {
            if (arr[i][j-1] == 0 && !isInZeroes(i, j-1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i, j-1));
                dive(arr, i, j-1, zeroes);
            }
        }
    }

    public void diveNorthWest(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (i-1 >= 0 && j-1 >= 0) {
            if (arr[i-1][j-1] == 0 && !isInZeroes(i-1, j-1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i-1, j-1));
                dive(arr, i-1, j-1, zeroes);
            }
        }
    }

    public void diveNorth(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (i-1 >= 0) {
            if (arr[i-1][j] == 0 && !isInZeroes(i-1, j, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i-1, j));
                dive(arr, i-1, j, zeroes);
            }
        }
    }

    public void diveNorthEast(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (i-1 >= 0 && j+1 < arr[0].length) {
            if (arr[i-1][j+1] == 0 && !isInZeroes(i-1, j+1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i-1, j+1));
                dive(arr, i-1, j+1, zeroes);
            }
        }
    }

    public void diveEast(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (j+1 < arr[0].length) {
            if (arr[i][j+1] == 0 && !isInZeroes(i, j+1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i, j+1));
                dive(arr, i, j+1, zeroes);
            }
        }
    }

    public void diveSouthEast(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (i+1 < arr.length && j+1 < arr[0].length) {
            if (arr[i+1][j+1] == 0 && !isInZeroes(i+1, j+1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i+1, j+1));
                dive(arr, i+1, j+1, zeroes);
            }
        }
    }

    public void diveSouth(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (i+1 < arr.length) {
            if (arr[i+1][j] == 0 && !isInZeroes(i+1, j, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i+1, j));
                dive(arr, i+1, j, zeroes);
            }
        }
    }

    public void diveSouthWest(int[][] arr, int i, int j, List<List<ZeroCoordinates>> zeroes) {
        if (i+1 < arr.length && j-1 >= 0) {
            if (arr[i+1][j-1] == 0 && !isInZeroes(i+1, j-1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new ZeroCoordinates(i+1, j-1));
                dive(arr, i+1, j-1, zeroes);
            }
        }
    }

    public Boolean isInZeroes(int i, int j, List<List<ZeroCoordinates>> zeroes) {
        for (List<ZeroCoordinates> chain: zeroes) {
            for (ZeroCoordinates coords: chain) {
                if (coords.i == i && coords.j == j) {
                    return true;
                }
            }
        }
        return false;
    }
}
