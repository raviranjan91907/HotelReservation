package ui;

import api.AdminResource;
import Model.*;

import java.util.Scanner;

public class AdminMenu {

    private static Scanner sc = new Scanner(System.in);
    private static AdminResource admin = AdminResource.getInstance();

    public static void start() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. See All Customers");
            System.out.println("2. See All Rooms");
            System.out.println("3. See All Reservations");
            System.out.println("4. Add A Room");
            System.out.println("5. Back to Main Menu");
            System.out.println("----------------------------------");
            System.out.println("Please Select a option:");
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
                case 1 -> {
                    admin.getAllCustomers().forEach(System.out::println);
                    break;
                }
                case 2 -> {
                    admin.getAllRooms().forEach(System.out::println);
                    break;
                }
                case 3 -> {
                    admin.displayAllReservations();
                    break;
                }
                case 4 ->{

                    addRoom();
                    break;
                }
                case 5 -> {
                    return;
                }
                default ->{
                    System.out.println("Invalid Valid Input");
                }
            }
        }
    }

    private static void addRoom() {

        boolean addAnotherRoom;

        do {
            String roomNumber;

            // ROOM NUMBER VALIDATION
            while (true) {
                System.out.println("Room Number:");
                roomNumber = sc.nextLine();

                if (roomNumber == null || roomNumber.trim().isEmpty()) {
                    System.out.println("Room number cannot be empty!");
                } else {
                    break;
                }
            }

            // PRICE VALIDATION
            double price;
            while (true) {
                System.out.println("Price:");

                try {
                    String input = sc.nextLine();

                    if (input == null || input.trim().isEmpty()) {
                        System.out.println("Price cannot be empty!");
                        continue;
                    }

                    price = Double.parseDouble(input);

                    if (price >= 0) break;

                    System.out.println("Price must be >= 0");

                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid number for price!");
                }
            }

            // ROOM TYPE VALIDATION
            RoomType roomType;
            while (true) {
                System.out.println("Type (1-SINGLE, 2-DOUBLE):");

                try {
                    int type = Integer.parseInt(sc.nextLine());

                    if (type == 1 || type == 2) {
                        roomType = (type == 1) ? RoomType.SINGLE : RoomType.DOUBLE;
                        break;
                    } else {
                        System.out.println("Enter only 1 or 2");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid number (1 or 2)!");
                }
            }

            // CREATE ROOM
            IRoom room = (price == 0)
                    ? new FreeRoom(roomNumber, roomType)
                    : new Room(roomNumber, price, roomType);

            // ADD ROOM LOGIC FIXED
            if (admin.addRoom(room)) {
                System.out.println("Room added!");
            } else {
                System.out.println("Room already exists!");
            }

            // ADD ANOTHER ROOM
            System.out.println("Would you like to add another room (Y/N)?");
            addAnotherRoom = sc.nextLine().equalsIgnoreCase("y");

        } while (addAnotherRoom);
    }
}