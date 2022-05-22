package atm;

public interface BanknoteHolder {
    void addBanknotes(int count);

    void removeBanknotes(int count);

    Banknote getBanknoteType();

    int getCapacity();

    int getBanknotesCount();

    int getTotalSum();
}
