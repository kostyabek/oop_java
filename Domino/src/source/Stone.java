package source;

public class Stone {
    private int leftValue;
    private int rightValue;
    boolean leftIsFree = true;
    boolean rightIsFree = true;

    public Stone(int leftValue, int rightValue) {
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public int getLeftValue() {
        return leftValue;
    }

    public int getRightValue() {
        return rightValue;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "leftValue=" + leftValue +
                ", rightValue=" + rightValue +
                '}';
    }
}
