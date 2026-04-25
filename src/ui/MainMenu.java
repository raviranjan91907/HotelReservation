package ui;

import api.HotelResource;
import Model.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    private static Scanner sc = new Scanner(System.in);
    private static HotelResource hotelResource = HotelResource.getInstance();
    private static Map<Integer,IRoom> availableRooms=new HashMap<>();

    public static void start() {
        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 ->{
                    findRoom();
                    break;
                }
                case 2 -> {
                    viewReservations();
                    break;
                }
                case 3 -> {
                    createAccount();
                    break;
                }
                case 4 -> {
                    AdminMenu.start();
                    break;
                }
                case 5 -> System.exit(0);
            }
        }
    }

    //create a new Account
    private static void createAccount() {
        System.out.println("Email:");
        String email = sc.nextLine();

        System.out.println("First Name:");
        String first = sc.nextLine();

        System.out.println("Last Name:");
        String last = sc.nextLine();

        hotelResource.createACustomer(email, first, last);
        System.out.println("Account created!");
    }

    //findRoom rooms and booking
    private static void findRoom() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            //Entering the checkin and checkout date
            System.out.println("Check-in (dd/MM/yyyy):");
            Date in = sdf.parse(sc.nextLine());

            System.out.println("Check-out (dd/MM/yyyy):");
            Date out = sdf.parse(sc.nextLine());

            Collection<IRoom> rooms = hotelResource.findARoom(in, out);




            while(true){

                //Displaying all the Available Room
                System.out.println("Available Rooms: ");
                for (IRoom room : rooms) {
                    System.out.println(room);
                }


                //Want to book a room or Not
                System.out.println("You want to book a room (Y/N)");
                String booking=sc.nextLine();
                if(booking.equalsIgnoreCase("y") || booking.equalsIgnoreCase("n")){

                    if(booking.equalsIgnoreCase("n")) {
                        break;
                    }
                    else{
                        System.out.println("Enter you email");
                        String email=sc.nextLine();


                        //Create Account if you don't have account
                        if(hotelResource.getCustomer(email)==null){
                            System.out.println("you don't Account you have to create");
                            createAccount();
                        }


                        //Room Booking
                        System.out.println("Enter Room Number of Available Rooms");
                        String roomNo=sc.nextLine();
                        IRoom availableRoom= hotelResource.getRoom(roomNo);
                        Reservation res=hotelResource.bookARoom(email,availableRoom,in,out);
                        System.out.println(res.toString());
                        System.out.println("Your room is Booked");


                        break;
                    }
                }
                else{
                    System.out.println("Invalid input!");
                }
            }

        } catch (Exception e) {
            System.out.println("Invalid input");
        }


    }


    //Displaying all the Reservations
    private static void viewReservations() {
        
        System.out.println("Email:");
        String email = sc.nextLine();

        for (Reservation r : hotelResource.getCustomersReservations(email)) {
            System.out.println(r);
        }
    }
}