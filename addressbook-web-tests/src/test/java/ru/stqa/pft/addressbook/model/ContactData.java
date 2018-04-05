package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String contactEmail;
    private final String mobile;
    private String group;

    public ContactData(String firstname, String lastname, String address, String contactEmail, String mobile, String group) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.contactEmail = contactEmail;
        this.mobile = mobile;
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
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

    public String getGroup() {
        return group;
    }
}
