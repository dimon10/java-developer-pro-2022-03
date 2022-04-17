package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final Comparator<? super Customer> byScores =
            (Customer c1, Customer c2) -> Long.compare(c1.getScores(), c2.getScores());
    private final TreeMap<Customer, String> customersData = new TreeMap<>(byScores);

    public Map.Entry<Customer, String> getSmallest() {
        var smallestEntry = customersData.firstEntry();
        return copyEntryWithImmutableKey(smallestEntry);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        var nextHigherEntry = customersData.higherEntry(customer);
        return copyEntryWithImmutableKey(nextHigherEntry);
    }

    public void add(Customer customer, String data) {
        customersData.put(customer, data);
    }

    private Map.Entry<Customer, String> copyEntryWithImmutableKey(Map.Entry<Customer, String> entry) {
        if (entry == null) return null;
        var customerKey = entry.getKey();
        var customerKeyCopy = new Customer(customerKey.getId(), customerKey.getName(), customerKey.getScores());
        return new AbstractMap.SimpleImmutableEntry<>(customerKeyCopy, entry.getValue());
    }
}
