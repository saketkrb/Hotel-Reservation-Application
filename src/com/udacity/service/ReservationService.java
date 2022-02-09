package com.udacity.service;

import com.udacity.model.Customer;
import com.udacity.model.IRoom;
import com.udacity.model.Reservation;
import java.util.*;

public class ReservationService {

    private static ReservationService reservationServiceInstance = null;

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (reservationServiceInstance == null)
            reservationServiceInstance = new ReservationService();
        return reservationServiceInstance;
    }

    private static final Map<String, IRoom> rooms = new HashMap<>();
    private static final Map<String, List<Reservation>> reservations = new HashMap<>();


    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        // fetch all existing reservations for customer
        List<Reservation> customerReservations = getCustomersReservation(customer);
        if (customerReservations == null)
            customerReservations = new ArrayList<>();
        // add the new reservation to customerReservation list
        customerReservations.add(newReservation);
        // update overall reservations
        reservations.put(customer.getEmail(), customerReservations);
        return newReservation;
    }

    Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        // first get all reservations
        List<Reservation> reservations = getAllReservation();
        if (reservations.isEmpty())
            return getAllRooms();
        List<IRoom> notAvailableRooms = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if ((checkRoomReserved(reservation, checkInDate, checkOutDate))) {
                notAvailableRooms.add(reservation.getRoom());
            }
        }

        Collection<IRoom> allRooms = getAllRooms();
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : allRooms) {
            String roomNumber = room.getRoomNumber();
            boolean roomAvailable = true;
            for (IRoom bookedRoom : notAvailableRooms) {
                if (Objects.equals(bookedRoom.getRoomNumber(), roomNumber)){
                    roomAvailable = false;
                }
                if (!roomAvailable)
                    break;
            }
            if (roomAvailable)
                availableRooms.add(room);
        }

        return availableRooms;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return findAvailableRooms(checkInDate, checkOutDate);
    }


    private boolean checkRoomReserved(Reservation reservation, Date checkInDate, Date checkOutDate) {
        return (checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate()));
    }

    public List<Reservation> getCustomersReservation(Customer customer) {
        if (customer == null)
            return null;
        return reservations.get(customer.getEmail());
    }

    public void printAllReservation() {
        List<Reservation> allReservations = getAllReservation();
        if (allReservations.isEmpty()) {
            System.out.println("No Reservations found!");
        } else {
            for (Reservation reservation : allReservations) {
                System.out.println(reservation);
            }
        }
    }
    public List<Reservation> getAllReservation() {
        List<Reservation> allReservations = new ArrayList<>();
        for (List<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }


}

















