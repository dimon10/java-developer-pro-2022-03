package homework;


import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {

    private final Deque<Customer> linkedList = new LinkedList<>();

    public void add(Customer customer) {
        linkedList.add(customer);
    }

    public Customer take() {
         return linkedList.pollLast();
    }
}
