package source;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Main mn = new Main();
        int[][] arr = new int[][]{
                new int[]{4,2,3,0,5,0,0,0,1,0},
                new int[]{1,1,1,1,0,1,3,4,0,1},
                new int[]{1,0,0,0,5,6,0,4,6,0},
                new int[]{1,0,3,2,0,5,0,0,1,1},
        };

        List<List<Integer[]>> zeroes = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                zero:
                if (arr[i][j] == 0) {
                    for (List<Integer[]> chain: zeroes) {
                        for (Integer[] coords: chain) {
                            if (coords[0] == i && coords[1] == j) {
                                break zero;
                            }
                        }
                    }
                    zeroes.add(new ArrayList<>());
                    zeroes.get(zeroes.size() - 1).add(new Integer[]{i, j});
                    mn.dive(arr, i, j, zeroes);
                }
            }
        }
        int maxSize = 0;
        for (List<Integer[]> chain: zeroes) {
            if (chain.size() > maxSize) {
                maxSize = chain.size();
            }
        }
        System.out.println(String.format("Maximum chain size is %d", maxSize));
    }

    public void dive(int[][] arr, int i, int j, List<List<Integer[]>> zeroes) {
        System.out.println(String.format("i=%d j=%d", i, j));

        //WEST
        if (j-1 >= 0) {
            if (arr[i][j-1] == 0 && !isInZeroes(i, j-1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i, j-1});
                dive(arr, i, j-1, zeroes);
            }
        }
        //NORTH-WEST
        if (i-1 >= 0 && j-1 >= 0) {
            if (arr[i-1][j-1] == 0 && !isInZeroes(i-1, j-1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i-1, j-1});
                dive(arr, i-1, j-1, zeroes);
            }
        }
        //NORTH
        if (i-1 >= 0) {
            if (arr[i-1][j] == 0 && !isInZeroes(i-1, j, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i-1, j});
                dive(arr, i-1, j, zeroes);
            }
        }
        //NORTH-EAST
        if (i-1 >= 0 && j+1 < arr[0].length) {
            if (arr[i-1][j+1] == 0 && !isInZeroes(i-1, j+1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i-1, j+1});
                dive(arr, i-1, j+1, zeroes);
            }
        }
        //EAST
        if (j+1 < arr[0].length) {
            if (arr[i][j+1] == 0 && !isInZeroes(i, j+1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i, j+1});
                dive(arr, i, j+1, zeroes);
            }
        }
        //SOUTH-EAST
        if (i+1 < arr.length && j+1 < arr[0].length) {
            if (arr[i+1][j+1] == 0 && !isInZeroes(i+1, j+1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i+1, j+1});
                dive(arr, i+1, j+1, zeroes);
            }
        }
        //SOUTH
        if (i+1 < arr.length) {
            if (arr[i+1][j] == 0 && !isInZeroes(i+1, j, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i+1, j});
                dive(arr, i+1, j, zeroes);
            }
        }
        //SOUTH-WEST
        if (i+1 < arr.length && j-1 >= 0) {
            if (arr[i+1][j-1] == 0 && !isInZeroes(i+1, j-1, zeroes)) {
                zeroes.get(zeroes.size() - 1).add(new Integer[]{i+1, j-1});
                dive(arr, i+1, j-1, zeroes);
            }
        }
    }

    public Boolean isInZeroes(int i, int j, List<List<Integer[]>> zeroes) {
        for (List<Integer[]> chain: zeroes) {
            for (Integer[] coords: chain) {
                if (coords[0] == i && coords[1] == j) {
                    return true;
                }
            }
        }
        return false;
    }
}
