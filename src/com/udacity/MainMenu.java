package com.udacity;

import com.udacity.api.HotelResource;
import com.udacity.model.IRoom;
import com.udacity.model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
    private static final HotelResource hotelResourceInstance = HotelResource.getInstance();
    private static final DateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    public static void mainMenu() {
        printMainMenu();
        String userChoice;
        try {
            do {
                userChoice = scanner.nextLine();
                if (userChoice.length() == 1) {
                    switch (userChoice.charAt(0)) {
                        case '1' -> findAndReserveRoom();
                        case '2' -> seeMyReservations();
                        case '3' -> createAccount();
                        case '4' -> AdminMenu.adminMenu();
                        case '5' -> {
                            System.out.println("Exiting");
                            System.exit(0);
                        }
                        default -> {
                            System.out.println("Please Select valid options");
                            printMainMenu();
                        }
                    }
                } else {
                    System.out.println("Back to menu!");
                    printMainMenu();
                }
            } while (userChoice.charAt(0) != '5');
        } catch(StringIndexOutOfBoundsException exception) {
            System.out.println("Empty Response, Exiting...");
        }
    }

    private static void seeMyReservations() {

        System.out.println("Please enter your registered email address");
        String customerEmail = scanner.nextLine();
        Collection<Reservation> customersReservations = hotelResourceInstance.getCustomersReservations(customerEmail);
        if (customersReservations == null) {
            System.out.println("No Reservations Found!");
        } else {
            for (Reservation reservation : customersReservations) {
                System.out.println(reservation);
            }
        }
        mainMenu();
    }

    private static void findAndReserveRoom() {

        System.out.println("Please Enter CheckIn Date (MM/dd/yyyy)");
        Date checkInDate = inputDate();

        System.out.println("Please Enter CheckOut Date (MM/dd/yyyy)");
        Date checkOutDate = inputDate();

        if (checkInDate != null && checkOutDate != null) {
            Collection<IRoom> availableRooms = hotelResourceInstance.findARoom(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                Collection<IRoom> alternateRooms = hotelResourceInstance.findAlternateRoom(checkInDate, checkOutDate);
                if (alternateRooms.isEmpty()) {
                    System.out.println("Sorry, No Rooms Found");
                    mainMenu();
                } else {
                    Date alternateCheckInDate = hotelResourceInstance.addDefaultDays(checkInDate);
                    Date alternateCheckOutDate = hotelResourceInstance.addDefaultDays(checkOutDate);
                    System.out.println("We Have found some rooms on alternate date range :"
                    + alternateCheckInDate + " - " + alternateCheckOutDate);
                    reserveRoom(alternateRooms, alternateCheckInDate, alternateCheckOutDate);
                }
            } else {
                printRooms(availableRooms);
                reserveRoom(availableRooms, checkInDate, checkOutDate);
            }
        } else {
            findAndReserveRoom();
        }
    }

    private static void reserveRoom(Collection<IRoom> availableRooms, Date checkInDate, Date checkOutDate) {

        System.out.println("Would you like to book? Y/N");
        String bookRoom = scanner.nextLine();
        if (bookRoom.equalsIgnoreCase("Y")) {
            System.out.println("Do you have an account with us? Y/N");
            String accountExist = scanner.nextLine();
            while (!((accountExist.equalsIgnoreCase("Y")) || (accountExist.equalsIgnoreCase("N")))) {
                System.out.println("Do you have an account with us? Y/N");
                accountExist = scanner.nextLine();
            }
            if (accountExist.equalsIgnoreCase("Y")) {
                System.out.println("Please provide your registered email address");
                String accountEmail = scanner.nextLine();
                if (hotelResourceInstance.getCustomer(accountEmail) != null) {
                    bookRoom(accountEmail, availableRooms, checkInDate, checkOutDate);
                } else {
                    System.out.println("User Account Doesn't exist, you may need to create a new account!");
                    createAccount();
                }
            } else {
                System.out.println("Please Create a account with us first");
                createAccount();
            }
        } else if (bookRoom.equalsIgnoreCase("N")) {
            mainMenu();
        } else {
            reserveRoom(availableRooms, checkInDate, checkOutDate);
        }
    }

    private static void bookRoom(String accountEmail, Collection<IRoom> availableRooms, Date checkInDate, Date checkOutDate) {
        printRooms(availableRooms);
        System.out.println("What room number would you like to reserve?");
        String roomNumber = AdminMenu.enterRoomNumber();
        boolean roomBooked = false;
        for (IRoom room : availableRooms) {
            if (Objects.equals(room.getRoomNumber(), roomNumber)) {
                IRoom selectedRoom = hotelResourceInstance.getRoom(roomNumber);
                Reservation bookingReservation = hotelResourceInstance.bookARoom(accountEmail, selectedRoom, checkInDate, checkOutDate);
                roomBooked = true;
                System.out.println("Reservation Confirmed!");
                System.out.println(bookingReservation);
            }
        }

        if (!roomBooked) {
            System.out.println("Room Number not available, Please Try Initiating Booking again!");
            mainMenu();
        }
    }


    private static void printRooms(Collection<IRoom> availableRooms) {
        for (IRoom room : availableRooms) {
            System.out.println(room.getRoomNumber());
        }
    }

    private static Date inputDate() {

        formatter.setLenient(false);
        try {
            return formatter.parse(MainMenu.scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Invalid Date, Kindly try again!");
            findAndReserveRoom();
        }
        return null;
    }

        private static void createAccount() {

            System.out.println("Please enter your email address, format : name@domain.com");
            String customerEmail = scanner.nextLine();
            System.out.println("Please enter your first name");
            String customerFirstName = scanner.nextLine();
            System.out.println("Please enter your last name");
            String customerLastName = scanner.nextLine();
        try {
            hotelResourceInstance.createACustomer(customerEmail, customerFirstName, customerLastName);
            System.out.println("Account added Successfully");
            System.out.println(hotelResourceInstance.getCustomer(customerEmail));
            mainMenu();
        }
        catch (IllegalArgumentException Exception) {
            System.out.println("Email format not valid, Kindly Enter in the format : name@domain.com");
            createAccount();
        }
    }

    public static void printMainMenu() {

        System.out.print("""
                Welcome to the Hotel Reservation Application
                -----------------------------------------------------
                1. Find and reserve a room\s
                2. See my reservations\s
                3. Create an Account\s
                4. Admin\s
                5. Exit\s
                -----------------------------------------------------
                Please select a number for the Menu option
                """);

    }

}
