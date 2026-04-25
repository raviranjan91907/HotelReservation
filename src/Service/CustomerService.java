package Service;

import Model.Customer;
import java.util.*;

public class CustomerService {
    private static CustomerService inst;
    private Map<String, Customer> cust = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (inst == null) {
            inst = new CustomerService();
        }
        return inst;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        cust.put(email, customer);
    }

    public Customer getCustomer(String email) {

        return cust.get(email);
    }

    public Collection<Customer> getAllCustomers() {
        return cust.values();
    }
}