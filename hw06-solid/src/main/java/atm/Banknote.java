package atm;

import lombok.Getter;

/**
 * Banknote with denomination.
 */
public enum Banknote {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20);

    @Getter
    private final Integer denomination;

    Banknote(Integer denomination) {
        this.denomination = denomination;
    }

    public static Banknote valueOfDenomination(Integer denomination) {
        for (Banknote e : values()) {
            if (e.getDenomination().equals(denomination)) {
                return e;
            }
        }
        return null;
    }
}
