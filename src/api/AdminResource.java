package api;

import Model.*;
import Service.*;

import java.util.*;

public class AdminResource {

    private static AdminResource instance;

    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }

    public void addRoom(IRoom room) {
        reservationService.addRoom(room);
    }
}