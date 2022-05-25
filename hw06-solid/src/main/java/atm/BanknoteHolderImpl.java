package atm;

import atm.exception.BanknoteHolderIsFull;
import atm.exception.NotEnoughBanknotesException;
import lombok.Getter;

/**
 * Holds 0 or more banknotes of particular denomination.
 */
@Getter
public class BanknoteHolderImpl implements BanknoteHolder {

    private final Banknote banknoteType;
    private final int capacity;
    private int banknotesCount;

    public BanknoteHolderImpl(Banknote banknoteType, int capacity) {
        this.banknoteType = banknoteType;
        this.capacity = capacity;
        this.banknotesCount = 0;
    }

    @Override
    public void addBanknotes(int count) {
        var newBanknotesCount = getBanknotesCount() + count;

        if (count < 0) {
            throw new IllegalArgumentException(String.format("Wrong count of banknotes: %d", count));
        }

        if (newBanknotesCount > getCapacity()) {
            throw new BanknoteHolderIsFull(String.format("Maximum banknote holder capacity exceeded: %d > %d",
                    newBanknotesCount, getCapacity()));
        }
        this.banknotesCount = newBanknotesCount;
    }

    @Override
    public void removeBanknotes(int count) {
        var newBanknotesCount = getBanknotesCount() - count;

        if (count < 0) {
            throw new IllegalArgumentException(String.format("Wrong count of banknotes: %d", count));
        }

        if (newBanknotesCount < 0) {
            throw new NotEnoughBanknotesException(String.format("Attempt to take from the banknote holder more than " +
                            "is available: %d > %d", count, getBanknotesCount()));
        }
        this.banknotesCount = newBanknotesCount;
    }

    @Override
    public int getTotalSum() {
        return getBanknotesCount() * getBanknoteType().getDenomination();
    }
}
