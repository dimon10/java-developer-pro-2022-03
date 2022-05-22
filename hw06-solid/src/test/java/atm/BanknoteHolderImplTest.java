package atm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BanknoteHolderImplTest {

    BanknoteHolder banknoteHolder;

    @Test
    void addBanknotes() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        banknoteHolder.addBanknotes(1);
        assertThat(banknoteHolder.getBanknotesCount()).isEqualTo(1);
        banknoteHolder.addBanknotes(4);
        assertThat(banknoteHolder.getBanknotesCount()).isEqualTo(5);
        banknoteHolder.addBanknotes(5);
        assertThat(banknoteHolder.getBanknotesCount()).isEqualTo(10);
    }

    @Test
    void addBanknotes_moreThanCapacity() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        banknoteHolder.addBanknotes(5);
        assertThatThrownBy(() -> banknoteHolder.addBanknotes(6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Maximum banknote holder capacity exceeded: 11 > 10");
    }

    @Test
    void removeBanknotes() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        banknoteHolder.addBanknotes(10);
        banknoteHolder.removeBanknotes(3);
        assertThat(banknoteHolder.getBanknotesCount()).isEqualTo(7);
        banknoteHolder.removeBanknotes(7);
        assertThat(banknoteHolder.getBanknotesCount()).isEqualTo(0);
    }

    @Test
    void removeBanknotes_lessThanExists() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        banknoteHolder.addBanknotes(5);
        assertThatThrownBy(() -> banknoteHolder.removeBanknotes(6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Attempt to take from the banknote holder more than is available: 6 > 5");
    }

    @Test
    void getTotalSum() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        banknoteHolder.addBanknotes(5);
        assertThat(banknoteHolder.getTotalSum()).isEqualTo(25);
    }

    @Test
    void getBanknoteType() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        assertThat(banknoteHolder.getBanknoteType()).isEqualTo(Banknote.FIVE);
    }

    @Test
    void getCapacity() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        assertThat(banknoteHolder.getCapacity()).isEqualTo(10);
    }

    @Test
    void getBanknotesCount() {
        banknoteHolder = new BanknoteHolderImpl(Banknote.FIVE, 10);
        assertThat(banknoteHolder.getBanknotesCount()).isEqualTo(0);
        banknoteHolder.addBanknotes(3);
        assertThat(banknoteHolder.getBanknotesCount()).isEqualTo(3);
    }
}