package Service;

import Model.*;

import java.util.*;

public class ReservationService {

    private static ReservationService inst;

    private Map<String, IRoom> rooms = new HashMap<>();
    private List<Reservation> reservations = new ArrayList<>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (inst == null) {
            inst = new ReservationService();
        }
        return inst;
    }

    public boolean addRoom(IRoom room) {
        if (!rooms.containsKey(room.getRoomNumber())) {
            rooms.put(room.getRoomNumber(), room);
            return true;
        }
        else{
            return false;
        }
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        for(Reservation reservation:reservations){
            if(reservation.getRoom().equals(room) &&
                    reservation.getCheckInDate().before(checkOut) &&
                    reservation.getCheckOutDate().after(checkIn)){
                throw new IllegalArgumentException("Room is already booked for these dates.");
            }
        }
        Reservation newReservation=new Reservation(customer,room,checkIn,checkOut);
        reservations.add(newReservation);
        return newReservation;
    }


    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        List<IRoom> availableRooms = new ArrayList<>(rooms.values());

        for(Reservation reservation:reservations){
            if(reservation.getCheckInDate().before(checkOut) &&
               reservation.getCheckOutDate().after(checkIn)){
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getCustomer().equals(customer)) {
                result.add(r);
            }
        }
        return result;
    }

    public void printAllReservation() {
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
}