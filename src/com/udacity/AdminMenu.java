package com.udacity;

import com.udacity.api.AdminResource;

import com.udacity.model.*;

import java.util.Collection;
import java.util.Collections;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class AdminMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminResource adminResourceInstance = AdminResource.getInstance();

    public static void adminMenu() {
        printAdminMenu();
        String adminChoice;
        try {
            do {
                adminChoice = scanner.nextLine();
                if (adminChoice.length() == 1) {
                    switch (adminChoice.charAt(0)) {
                        case '1' -> seeAllCustomers();
                        case '2' -> seeAllRooms();
                        case '3' -> seeAllReservations();
                        case '4' -> addARoom();
                        case '5' -> MainMenu.mainMenu();
                        case '6' -> {
                            System.out.println("Exiting");
                            System.exit(0);
                        }
                        default -> {
                            System.out.println("Please Select valid options");
                            printAdminMenu();
                        }
                    }
                } else {
                    System.out.println("Back to menu!");
                    printAdminMenu();
                }
            } while (adminChoice.charAt(0) != '6');
        } catch(StringIndexOutOfBoundsException exception) {
            System.out.println("Empty Response, Exiting...");
        }
    }

    private static void addARoom() {

        System.out.println("Please Enter Room Number");
        String roomNumber = enterRoomNumber();
        System.out.println("Enter price per night");
        Double pricePerNight = enterRoomPrice(scanner);
        System.out.println("Enter room type: 1 for single bed, 2 for double bed");
        RoomType roomType = enterRoomType(scanner);
        IRoom room = new Room(roomNumber, pricePerNight, roomType);
        adminResourceInstance.addRoom(Collections.singletonList(room));
        System.out.println("New Room Added Successfully!");
        System.out.println(room);
        System.out.println("Would you like to add another room? Y/N");
        addMoreRooms();
    }

    private static void addMoreRooms() {
        String addmorerooms;
        try {
            addmorerooms = scanner.nextLine();
            while (addmorerooms.length() != 1) {
                System.out.println("Please enter Y (Yes) or N (No)");
                addmorerooms = scanner.nextLine();
            }
            if (addmorerooms.charAt(0) == 'Y') {
                addARoom();
            } else if (addmorerooms.charAt(0) == 'N'){
                adminMenu();
            } else {
                System.out.println("Please enter Y (Yes) or N (No)");
                addMoreRooms();
            }
        } catch (StringIndexOutOfBoundsException Exception) {
            System.out.println("Empty Response, Please Try Again!");
            addMoreRooms();
        }

    }

    private static RoomType enterRoomType(Scanner scanner) {
        try {
            return RoomType.valueOfLabel(scanner.nextLine());
        } catch (IllegalArgumentException exception) {
            System.out.println("Please Enter a valid room type, either 1 for single or 2 for double");
            return enterRoomType(scanner);
        }
    }

    private static Double enterRoomPrice(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("Please Enter a valid room price");
            return enterRoomPrice(scanner);
        }
    }

    static String enterRoomNumber() {
        String input;
        do {
            input = AdminMenu.scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Bad Input, TRY AGAIN!");
            }
            else if (onlyDigits(input) ) {
                return input;
                }
            else {
                System.out.println("Please Enter Only Numeric Values");
            }
        } while (true);
    }



    private static boolean onlyDigits(String roomNumber) {
        Pattern pattern = regexPattern.getDigitsPattern();
        Matcher matcher = pattern.matcher(roomNumber);
        return matcher.matches();
    }


    private static void seeAllReservations() {
        adminResourceInstance.displayAllReservations();
        adminMenu();
    }

    private static void seeAllRooms() {
        Collection<IRoom> allRooms = adminResourceInstance.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("No Rooms Found!");
        } else {
            for (IRoom room : allRooms) {
                System.out.println(room);
            }
        }
        adminMenu();
    }

    private static void seeAllCustomers() {
        Collection<Customer> customersList = adminResourceInstance.getAllCustomers();
        if (customersList.isEmpty()) {
            System.out.println("No Customer Found!");
        } else {
            for (Customer customer : customersList) {
                System.out.println(customer);
            }
        }
        adminMenu();
    }


    public static void printAdminMenu() {

        System.out.print("""
                Admin Menu
                -----------------------------------------------------
                1. See all Customers\s
                2. See all Rooms\s
                3. See all Reservations\s
                4. Add a room\s
                5. Back to Main Menu\s
                6. Exit\s
                -----------------------------------------------------
                Please select a number for the Menu option
                """);
    }

}
