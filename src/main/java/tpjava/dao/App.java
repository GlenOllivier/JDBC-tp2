package tpjava.dao;

import tpjava.dao.dal.AddressDAO;
import tpjava.dao.dal.ContactDAO;
import tpjava.dao.domain.Address;
import tpjava.dao.domain.Contact;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {

        Address a1 = new Address("14", "rue Victor Hugo", "Brest", "29200", "France");
        Address a2 = new Address("12", "rue des pansements", "St Jean du doigt", "29540", "France");

        Contact c1 = new Contact("glen.ollivier@gmail.com", "Glen", "Ollivier", a1);
        Contact c2 = new Contact("fañch.mitt@assemblee.gouv.fr", "Fañch", "Mitterand", null);
        Contact c3 = new Contact("jean.roalec@nanarland.fr", "Jean", "Roalec", a2);

        AddressDAO adao = new AddressDAO();
        adao.create(a1);

        ContactDAO cdao = new ContactDAO();
        cdao.create(c1);
        cdao.update(c2);
        cdao.create(c3);

        c2.setAddress(a1);
        cdao.update(c2);

        cdao.delete(c1);
        cdao.delete(c2);
        cdao.delete(c3);
        adao.delete(a1);
        adao.delete(a2);
        c1 = cdao.findById(2);
        System.out.println(c1.getEmail());
        a1 = adao.findById(2);
        System.out.println(a1.toString());
    }
}