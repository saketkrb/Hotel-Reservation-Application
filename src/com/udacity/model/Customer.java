package com.udacity.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.udacity.model.regexPattern.getEmailPattern;

public class Customer {


    private String firstName;
    private String lastName;
    private String email;


    public Customer(String firstName, String lastName, String email) {
        Pattern pattern =  getEmailPattern();
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
