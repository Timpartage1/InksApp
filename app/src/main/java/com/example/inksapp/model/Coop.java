package com.example.inksapp.model;

public class Coop {

    private String firstname;
    private String lastname;
    private String email;
    private int phone;
    private String password;
    private String gender;
    private String province; String	district;
    private int street_number;
    private int poultry_size	;
    private String poultry_name;
    private String cdate;

    public Coop() {
    }

    public Coop(String firstname, String lastname, String email, int phone, String password, String gender, String province, String district, int street_number, int poultry_size, String poultry_name, String cdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
        this.province = province;
        this.district = district;
        this.street_number = street_number;
        this.poultry_size = poultry_size;
        this.poultry_name = poultry_name;
        this.cdate = cdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getStreet_number() {
        return street_number;
    }

    public void setStreet_number(int street_number) {
        this.street_number = street_number;
    }

    public int getPoultry_size() {
        return poultry_size;
    }

    public void setPoultry_size(int poultry_size) {
        this.poultry_size = poultry_size;
    }

    public String getPoultry_name() {
        return poultry_name;
    }

    public void setPoultry_name(String poultry_name) {
        this.poultry_name = poultry_name;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
}
