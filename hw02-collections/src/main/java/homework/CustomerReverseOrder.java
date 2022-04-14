package homework;


import java.util.LinkedList;

public class CustomerReverseOrder {

    private final LinkedList<Customer> linkedList = new LinkedList<>();

    public void add(Customer customer) {
        linkedList.add(customer);
    }

    public Customer take() {
         return linkedList.pollLast();
    }
}
