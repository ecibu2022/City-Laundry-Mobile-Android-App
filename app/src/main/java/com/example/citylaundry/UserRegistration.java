package com.example.citylaundry;

public class UserRegistration {
    String firstName, lastName, email, phone, address, username, kin, userNO, gender, role;

    public UserRegistration() {
    }

    public UserRegistration(String firstName, String lastName, String email, String phone, String address, String username, String kin, String userNO, String gender, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.username = username;
        this.kin = kin;
        this.userNO = userNO;
        this.gender = gender;
        this.role = role;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKin() {
        return kin;
    }

    public void setKin(String kin) {
        this.kin = kin;
    }

    public String getUserNO() {
        return userNO;
    }

    public void setUserNO(String userNO) {
        this.userNO = userNO;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
