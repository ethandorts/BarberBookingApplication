package com.example.barberbookingapplication;

import java.util.Date;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String DOB;

    public Customer() {

    }

    public Customer(String firstName, String lastName, String email, String phoneNumber, String DOB) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.DOB = DOB;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return DOB;
    }

    public void setDateOfBirth(String DOB) {
        this.DOB = DOB;
    }
}
