package tpjava.dao.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Address {
    private int id;
    private String number;
    private String street;
    private String city;
    private String zipcode;
    private String country;
    private Set<Contact> contacts;

    public Address(String number, String street, String city, String zipcode, String country) {
        this.id = 0;
        this.number = number;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
        this.contacts = new HashSet<Contact>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id == address.id &&
                number.equals(address.number) &&
                street.equals(address.street) &&
                city.equals(address.city) &&
                zipcode.equals(address.zipcode) &&
                country.equals(address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, street, city, zipcode, country);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", zipcode='").append(zipcode).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void add(Contact c) {
        contacts.add(c);
        c.setAddress(this);
    }

    public void remove(Contact c) {
        contacts.remove(c);
        c.setAddress(null);
    }
}