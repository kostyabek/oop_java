package hierarchy;

// #45

public enum Currency {
    USD(0.036),
    EUR(0.03),
    RUB(2.7),
    UAH(1),
    GBP(0.026),
    PLN(0.13);

    double rate;

    Currency(double rate) {
        this.rate = rate;
    }
}
