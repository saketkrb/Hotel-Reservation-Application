package com.udacity.api;

import com.udacity.model.Customer;
import com.udacity.model.IRoom;
import com.udacity.model.Reservation;
import com.udacity.service.CustomerService;
import com.udacity.service.ReservationService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource hotelResourceInstance = null;
    private static final int DEFAULT_DAYS_ADD = 7;
    private HotelResource() {}

    public static HotelResource getInstance() {
        if (hotelResourceInstance == null)
            hotelResourceInstance = new HotelResource();
        return hotelResourceInstance;
    }

    private final CustomerService customerServiceInstance = CustomerService.getInstance();
    private final ReservationService reservationServiceInstance = ReservationService.getInstance();

    public Customer getCustomer(String email) {
        return customerServiceInstance.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerServiceInstance.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationServiceInstance.getARoom(roomNumber);
    }


    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        return reservationServiceInstance.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationServiceInstance.getCustomersReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationServiceInstance.findRooms(checkInDate, checkOutDate);
    }

    public Collection<IRoom> findAlternateRoom(Date checkInDate, Date checkOutDate) {
        return reservationServiceInstance.findRooms(addDefaultDays(checkInDate), addDefaultDays(checkOutDate));
    }

    public Date addDefaultDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, DEFAULT_DAYS_ADD);
        return calendar.getTime();
    }

}
