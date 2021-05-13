public enum Denomination {
    THOUSAND(1000),
    FIVE_HUNDRED(500),
    TWO_HUNDRED(200),
    HUNDRED(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10),
    FIVE(5),
    TWO(2),
    ONE(1),
    COIN_FIFTY(0.5),
    COIN_TEN(0.1);

    double denomination;

    Denomination(double denomination)
    {
        this.denomination = denomination;
    }
}
