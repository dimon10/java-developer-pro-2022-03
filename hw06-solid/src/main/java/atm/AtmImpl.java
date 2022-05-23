package atm;

import atm.exception.BanknoteNotAcceptException;
import atm.exception.SumExceedException;

import java.util.*;

public class AtmImpl implements Atm {

    private final Map<Banknote, BanknoteHolder> banknoteHolders = new HashMap<>();

    public AtmImpl(BanknoteHolder... banknoteHolders) {
        if (banknoteHolders == null || banknoteHolders.length == 0) {
            throw new IllegalArgumentException("Error during Atm initialization. BanknoteHolders must be defined.");
        }

        var uniqueBanknoteTypes = Arrays.stream(banknoteHolders)
                .map(BanknoteHolder::getBanknoteType)
                .distinct().toList();
        if (uniqueBanknoteTypes.size() < banknoteHolders.length) {
            throw new IllegalArgumentException("Error during Atm initialization. BanknoteHolders list must consists of " +
                    "elements with unique banknote types.");
        }

        for (var banknoteHolder : banknoteHolders) {
            this.banknoteHolders.put(banknoteHolder.getBanknoteType(), banknoteHolder);
        }
    }

    @Override
    public void acceptCash(Banknote... banknotes) {
        if (banknotes == null || banknotes.length == 0) {
            throw new IllegalArgumentException("If you want to load cash, please define list of banknotes parameter: "
                    + Arrays.toString(banknotes));
        }

        int acceptedSum = 0;
        var notAcceptedBanknotes = new ArrayList<>();
        for (Banknote banknote : banknotes) {
            try {
                var banknoteHolder = banknoteHolders.get(banknote);

                if (banknoteHolder == null) {
                    throw new BanknoteNotAcceptException("This banknote type is not accepted: " + banknote);
                }

                banknoteHolder.addBanknotes(1);
                acceptedSum += banknote.getDenomination();
            } catch (Exception e) {
                notAcceptedBanknotes.add(banknote);
            }
        }

        if (!notAcceptedBanknotes.isEmpty()) {
            System.out.println("Some of banknotes has been not accepted: " + notAcceptedBanknotes);
        }

        System.out.println(String.format("%d of money has been added to your account.", acceptedSum));
    }

    @Override
    public List<Banknote> giveOutCash(int sum) {
        if (sum < 1) {
            throw new IllegalArgumentException("Requested sum less than 1");
        }

        var balance = getBalance();
        if (sum > balance) {
            throw new SumExceedException(String.format("Requested sum more than Atm balance: %d > %d", sum, balance));
        }

        var banknotes = AtmCalculator.checkForCashOutOperation(this.banknoteHolders.values(), sum);
        banknotes.forEach(banknote -> {
            removeBanknoteFromAtm(banknote);
            System.out.println("You get banknote: " + banknote);
        });

        printBalance();
        return banknotes;
    }

    @Override
    public void printBalance() {
        System.out.println(String.format("Atm has %d amount of money.", getBalance()));
    }

    @Override
    public int getBalance() {
        return banknoteHolders.values().stream()
                .map(BanknoteHolder::getTotalSum)
                .reduce(0, Integer::sum);
    }

    private void removeBanknoteFromAtm(Banknote banknote) {
        banknoteHolders.get(banknote).removeBanknotes(1);
    }
}
