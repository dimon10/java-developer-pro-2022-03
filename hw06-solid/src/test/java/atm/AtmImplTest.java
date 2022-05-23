package atm;

import atm.exception.CannotChangeSumException;
import atm.exception.SumExceedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AtmImplTest {

    Atm atm;

    @BeforeEach
    void setUp() {
        var holder1 = new BanknoteHolderImpl(Banknote.ONE, 30); // total 30
        var holder2 = new BanknoteHolderImpl(Banknote.TWO, 15); // total 30
        // holder5 is missed for special test conditions
        var holder10 = new BanknoteHolderImpl(Banknote.TEN, 10); // total 100
        var holder20 = new BanknoteHolderImpl(Banknote.TWENTY, 5); // total 100
        atm = new AtmImpl(holder1, holder2, holder10, holder20);
    }

    @Test
    void acceptCash() {
        assertThat(atm.getBalance()).isEqualTo(0);

        var cash1 = new Banknote[]{Banknote.TWENTY, Banknote.TEN, Banknote.TWO, Banknote.TWO, Banknote.ONE};
        var sum1 = Arrays.stream(cash1).map(Banknote::getDenomination).reduce(0, Integer::sum);
        atm.acceptCash(cash1);
        assertThat(atm.getBalance()).isEqualTo(sum1);

        var cash2 = new Banknote[]{Banknote.TWENTY, Banknote.TWENTY, Banknote.TEN, Banknote.TEN, Banknote.ONE};
        var sum2 = Arrays.stream(cash2).map(Banknote::getDenomination).reduce(0, Integer::sum);
        atm.acceptCash(cash2);
        assertThat(atm.getBalance()).isEqualTo(sum1 + sum2);
    }

    @Test
    void acceptCash_nonAcceptableBanknotes() {
        assertThat(atm.getBalance()).isEqualTo(0);

        var cash1 = new Banknote[]{Banknote.TWENTY, Banknote.TEN, Banknote.FIVE, Banknote.FIVE, Banknote.FIVE};
        var acceptedSum1 = Banknote.TWENTY.getDenomination() + Banknote.TEN.getDenomination();
        atm.acceptCash(cash1);
        assertThat(atm.getBalance()).isEqualTo(acceptedSum1);
    }

    @Test
    void getBalance() {
        assertThat(atm.getBalance()).isEqualTo(0);
        atm.acceptCash(Banknote.TEN);
        assertThat(atm.getBalance()).isEqualTo(10);
    }

    @Test
    void giveOutCash1() {
        atm.acceptCash(Banknote.TWENTY,                     // 20
                Banknote.TEN, Banknote.TEN,                 // 20
                Banknote.TWO, Banknote.TWO, Banknote.TWO,   // 6
                Banknote.ONE, Banknote.ONE, Banknote.ONE);  // 3
        var initialBalance = atm.getBalance();
        atm.giveOutCash(17);
        assertThat(atm.getBalance()).isEqualTo(initialBalance - 17);
    }

    @Test
    void giveOutCash2() {
        atm.acceptCash(Banknote.TWENTY,                     // 20
                Banknote.TEN, Banknote.TEN,                 // 20
                Banknote.TWO, Banknote.TWO, Banknote.TWO,   // 6
                Banknote.ONE, Banknote.ONE, Banknote.ONE);  // 3
        var initialBalance = atm.getBalance();
        atm.giveOutCash(39);
        assertThat(atm.getBalance()).isEqualTo(initialBalance - 39);
    }

    @Test
    void giveOutCash3() {
        atm.acceptCash(Banknote.TWENTY, Banknote.ONE);
        var initialBalance = atm.getBalance();
        atm.giveOutCash(21);
        assertThat(atm.getBalance()).isEqualTo(initialBalance - 21);
    }

    @Test
    void giveOutCash_cannotCashOut_NotEnoughBanknotes() {
        atm.acceptCash(Banknote.TWENTY,
                Banknote.TEN, Banknote.TEN,
                Banknote.TWO, Banknote.TWO, Banknote.TWO,
                Banknote.ONE);
        assertThatThrownBy(() -> atm.giveOutCash(19)).isInstanceOf(CannotChangeSumException.class);
    }

    @Test
    void giveOutCash_cannotCashOut_SumExceed() {
        atm.acceptCash(Banknote.TWENTY, Banknote.ONE);
        assertThatThrownBy(() -> atm.giveOutCash(22)).isInstanceOf(SumExceedException.class);
    }
}