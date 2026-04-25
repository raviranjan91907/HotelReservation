package api;

import Model.*;
import Service.*;

import java.util.*;

public class HotelResource {

    private static HotelResource instance;

    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {}

    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String email, IRoom room, Date checkIn, Date checkOut) {
        Customer customer = customerService.getCustomer(email);
        return reservationService.reserveARoom(customer, room, checkIn, checkOut);
    }

    public Collection<Reservation> getCustomersReservations(String email) {
        Customer customer = customerService.getCustomer(email);
        return reservationService.getCustomersReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}