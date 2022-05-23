package atm;

import java.util.List;

/**
 * Atm can accept banknotes, give out banknotes and inform about it balance.
 */
public interface Atm {

    /**
     * Loads Atm with banknotes.
     *
     * @param banknotes
     */
    void acceptCash(Banknote... banknotes);

    /**
     * Gives out banknotes for particular sum if it's possible.
     * Throws {@link atm.exception.NotEnoughBanknotesException} if can't serve because there are no enough banknotes.
     * Throws {@link atm.exception.SumExceedException} if input sum more than available atm balance.
     *
     * @param sum
     * @return
     */
    List<Banknote> giveOutCash(int sum);

    /**
     * Prints Atm balance.
     */
    void printBalance();

    /**
     * Return available Atm balance.
     *
     * @return
     */
    int getBalance();
}
