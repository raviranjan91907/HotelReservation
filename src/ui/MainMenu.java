package ui;

import api.HotelResource;
import Model.*;

import java.text.ParseException;
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
            System.out.println("-------------------------------------");
            System.out.println("Please Select a option");


            int choice;
            while (true) {
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number:");
                }
            }



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
                default -> System.out.println("Invalid Input");
            }

        }
    }

    //create a new Account
    private static void createAccount() {
        while(true){
        try {

            System.out.println("Enter Email Format example@gmail.com:");
            String email = sc.nextLine();

            System.out.println("First Name:");
            String first = sc.nextLine();

            System.out.println("Last Name:");
            String last = sc.nextLine();

            hotelResource.createACustomer(email, first, last);

            System.out.println("Account created!");
            System.out.println("Welcome Hotel Application");
            break;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Please Enter valid details. \n");
        }
    }

    }

    //findRoom rooms and booking
    private static void findRoom() {
        Date in=null;
        Date out=null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        Date today;
        try {
            today = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        while(true){
            String userIndate;
            while(true) {
                System.out.println("Enter the CheckIndate in format(dd/MM/yyyy)");
                userIndate = sc.nextLine();
                if (userIndate == null || userIndate.trim().isEmpty()) {
                    System.out.println("CheckIn Date should not be empty or null");
                }else{
                    try{
                        in = sdf.parse(userIndate);
                        if(in.before(today)){
                            System.out.println("Past Date is not Allowed!");
                            continue;
                        }
                        break;
                    }
                    catch (ParseException e) {
                        System.out.println("Invalid date format or value. Try again.");
                    }
                }
            }

            String userOutDate;
            while(true) {
                System.out.println("Enter the CheckOut Date in formate(dd/MM/yyyy)");
                userOutDate = sc.nextLine();
                if (userOutDate == null || userOutDate.trim().isEmpty()) {
                    System.out.println("CheckOut Date should not be empty or null");
                }
                else{
                    try{
                        out = sdf.parse(userOutDate);
                        break;
                    }
                    catch (ParseException e) {
                        System.out.println("Invalid date format or value. Try again.");
                    }
                }
            }

            if(out.after(in)){
                break;
            }else{
                System.out.println("CheckIn date should be before CheckOut");
            }
        }






        while(true){
        //Displaying all the Available Room
        Collection<IRoom> rooms = hotelResource.findARoom(in, out);
        Date recommendedCheckInDate;
        Date recommendedCheckoutDate;
        if(rooms.isEmpty()){
            System.out.println("No Rooms is Available on Date "+in+" to "+out);

            Calendar calendar=Calendar.getInstance();
            calendar.setTime(in);
            calendar.add(Calendar.DATE,7);
            recommendedCheckInDate=calendar.getTime();

            calendar.setTime(out);
            calendar.add(Calendar.DATE,7);
            recommendedCheckoutDate=calendar.getTime();

            rooms = hotelResource.findARoom(recommendedCheckInDate, recommendedCheckoutDate);



            System.out.println("Do you want to Booking room on Date "+recommendedCheckInDate+" to "+recommendedCheckoutDate+" ? (Y/N)" );
            if(sc.nextLine().equalsIgnoreCase("n")){
                break;
            }
            else{
                if(rooms.isEmpty()){
                    System.out.println("No Available booking on Date "+recommendedCheckInDate+" to "+recommendedCheckoutDate);
                    break;
                }
                in=recommendedCheckInDate;
                out=recommendedCheckoutDate;
            }

        }


        System.out.println("Available Rooms: ");
        int i=1;
        for (IRoom room : rooms) {
            System.out.println(i+" "+room+"\n");
            i++;
        }


        //Want to book a room or Not
        System.out.println("You want to book a room (Y/N)");
        String booking=sc.nextLine();
        if(booking.equalsIgnoreCase("y") || booking.equalsIgnoreCase("n")){

        if(booking.equalsIgnoreCase("n")) {
            break;
        }
        else{
            String email;
            while(true) {
                System.out.println("Enter your email");
                email = sc.nextLine();
                if(email==null || email.trim().isEmpty()){
                    System.out.println("Email Should not be null");
                    continue;
                }
                break;
            }

            //Create Account if you don't have account
            if(hotelResource.getCustomer(email)==null){
                System.out.println("you don't have any Account you have to create");
                createAccount();
            }


                        //Room Booking
            while(true) {
                try {
                    System.out.println("Enter Room Number you want to book");
                    String roomNo = sc.nextLine();
                    if(roomNo==null || roomNo.trim().isEmpty()) {
                        System.out.println("roomNo should not be empty");
                        continue;
                    }

                    IRoom availableRoom = hotelResource.getRoom(roomNo);

                    if(availableRoom==null){
                        System.out.println("Room Dose not exist");
                        continue;
                    }


                    Reservation res = hotelResource.bookARoom(email, availableRoom, in, out);
                    System.out.println(res.toString());
                    System.out.println("Your room is Booked");
                    break;
                }
                catch(IllegalArgumentException e){
                    System.out.println(e.getMessage()+"\n"+"Try with different room");
                }
            }
        }
        }
        else{
            System.out.println("Invalid input!");
        }
            System.out.println("\n----------------------------------------------------");
        }

    }


    //Displaying all the Reservations
    private static void viewReservations() {

        System.out.println("Email:");
        String email = sc.nextLine();
        int i=0;
        for (Reservation r : hotelResource.getCustomersReservations(email)) {
            System.out.println(r);
            i++;
        }
        if(i==0){
            System.out.println("no Reservation found or customer is not exist");
        }
    }
}