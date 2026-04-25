package ui;

import api.AdminResource;
import Model.*;

import java.util.Scanner;

public class AdminMenu {

    private static Scanner scanner = new Scanner(System.in);
    private static AdminResource admin = AdminResource.getInstance();

    public static void start() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back");

            int choice = Integer.parseInt(scanner.nextLine());

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
        System.out.println("Room Number:");
        String number = scanner.nextLine();

        System.out.println("Price:");
        double price = Double.parseDouble(scanner.nextLine());

        RoomType roomType;
        while(true) {
            System.out.println("Type (1-SINGLE, 2-DOUBLE):");
            int type = Integer.parseInt(scanner.nextLine());
            if(type==1 || type==2) {
                roomType = (type == 1) ? RoomType.SINGLE : RoomType.DOUBLE;
                break;
            }
            else{
                System.out.println("Invalid input!");
            }
        }
        IRoom room = (price == 0)
                ? new FreeRoom(number, roomType)
                : new Room(number, price, roomType);

        admin.addRoom(room);

        System.out.println("Room added!");
    }
}