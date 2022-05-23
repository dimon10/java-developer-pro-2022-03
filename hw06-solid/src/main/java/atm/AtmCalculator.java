package atm;

import atm.exception.CannotCashOutSumException;

import java.util.*;
import java.util.stream.Collectors;

public final class AtmCalculator {

    /**
     * Tries to cash out particular sum with available banknotes in the banknote holders.
     * Throw {@link CannotCashOutSumException} if cash out operation is not successful
     *
     * @param banknoteHolders
     * @param sum
     * @return array of banknotes if change operation is successful
     */
    public static List<Banknote> checkForCashOutOperation(Collection<BanknoteHolder> banknoteHolders, int sum) {
        // SortedMap<Integer, Integer> - key is denomination banknote with reverse ordering, value is count of banknotes.
        SortedMap<Integer, Integer> banknotes = new TreeMap<>(Comparator.reverseOrder());
        banknotes.putAll(banknoteHolders.stream()
                .filter(banknoteHolder -> banknoteHolder.getBanknotesCount() > 0)
                .collect(Collectors.toMap(e -> e.getBanknoteType().getDenomination(), BanknoteHolder::getBanknotesCount)));

        var result = new ArrayList<Banknote>();
        try {
            while (sum > 0) {
                Integer nextBanknote = returnNextValue(sum, banknotes.keySet());
                removeBanknote(banknotes, nextBanknote);
                result.add(Banknote.valueOfDenomination(nextBanknote));
                sum = sum - nextBanknote;
                if (sum == 0) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new CannotCashOutSumException("Cannot change sum with available banknotes. Please choose another sum.");
        }
        return result;
    }

    private static void removeBanknote(Map<Integer, Integer> banknotes, Integer nextBanknote) {
        var banknotesCount = banknotes.get(nextBanknote);
        banknotesCount--;
        if (banknotesCount == 0) {
            banknotes.remove(nextBanknote);
        } else {
            banknotes.put(nextBanknote, banknotesCount);
        }
    }

    private static Integer returnNextValue(Integer sum, Set<Integer> availableValues) {
        return availableValues.stream()
                .filter(value -> value <= sum)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }
}
