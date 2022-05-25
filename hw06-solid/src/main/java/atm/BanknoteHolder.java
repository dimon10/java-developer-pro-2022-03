package atm;

/**
 * BanknoteHolder is a banknote storage.
 * It has banknote type, capacity and can add and remove banknotes to/from itself.
 */
public interface BanknoteHolder {

    void addBanknotes(int count);

    void removeBanknotes(int count);

    Banknote getBanknoteType();

    int getCapacity();

    int getBanknotesCount();

    int getTotalSum();
}
