package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String contactEmail;
    private final String mobile;

    public ContactData(String firstname, String lastname, String address, String contactEmail, String mobile) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.contactEmail = contactEmail;
        this.mobile = mobile;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getMobile() {
        return mobile;
    }
}
