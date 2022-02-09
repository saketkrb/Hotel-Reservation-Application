package com.udacity.api;

import com.udacity.model.Customer;
import com.udacity.model.IRoom;
import com.udacity.service.CustomerService;
import com.udacity.service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource adminResourceInstance = null;

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (adminResourceInstance == null)
            adminResourceInstance = new AdminResource();
        return adminResourceInstance;
    }

    private final CustomerService customerServiceInstance = CustomerService.getInstance();
    private final ReservationService reservationServiceInstance = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerServiceInstance.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationServiceInstance.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        return reservationServiceInstance.getAllRooms();
    }


    public Collection<Customer> getAllCustomers() {
        return customerServiceInstance.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationServiceInstance.printAllReservation();
    }

}
