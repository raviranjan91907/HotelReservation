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

    public void addRoom(IRoom room) {
        if (!rooms.containsKey(room.getRoomNumber())) {
            rooms.put(room.getRoomNumber(), room);
        }
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        if (isRoomAvailable(room, checkIn, checkOut)) {
            Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
            reservations.add(reservation);
            return reservation;
        }
        return null;
    }

    private boolean isRoomAvailable(IRoom room, Date checkIn, Date checkOut) {
        for (Reservation r : reservations) {
            if (r.getRoom().equals(room)) {
                if (!(checkOut.before(r.getCheckInDate()) || checkIn.after(r.getCheckOutDate()))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        List<IRoom> available = new ArrayList<>();

        for (IRoom room : rooms.values()) {
            if (isRoomAvailable(room, checkIn, checkOut)) {
                available.add(room);
            }
        }

        if (available.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(checkIn);
            cal.add(Calendar.DATE, 7);
            Date newCheckIn = cal.getTime();

            cal.setTime(checkOut);
            cal.add(Calendar.DATE, 7);
            Date newCheckOut = cal.getTime();

            return findRooms(newCheckIn, newCheckOut);
        }

        return available;
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