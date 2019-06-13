package tpjava.dao.dal;

import tpjava.dao.domain.Address;
import tpjava.dao.domain.Contact;

import java.sql.*;

public class ContactDAO {

    private static final String SELECT_QUERY = "SELECT * FROM t_contact_con WHERE con_id = ?;";
    private static final String INSERT_QUERY = "INSERT INTO t_contact_con (con_email, con_first_name, con_last_name, add_id) VALUES (?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE t_contact_con SET con_email = ?, con_first_name = ?, con_last_name = ?, add_id = ? WHERE con_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM t_contact_con WHERE con_id = ?;";


    public void create(Contact c) throws SQLException {

        Connection connection = PersistenceManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, c.getEmail());
            statement.setString(2, c.getFirstName());
            statement.setString(3, c.getLastName());
            Address address = c.getAddress();
            if (address != null) {
                if (address.getId() == 0) {
                    AddressDAO adao = new AddressDAO();
                    adao.create(address);
                }
                statement.setInt(4, address.getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            c.setId(resultSet.getInt(1));
            resultSet.close();
        }
    }

    //TODO : methods
    public void update(Contact c) throws SQLException {

        if (c.getId() == 0) {
            create(c);
            return;
        }

        Connection connection = PersistenceManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, c.getEmail());
            statement.setString(2, c.getFirstName());
            statement.setString(3, c.getLastName());
            Address address = c.getAddress();
            if (address != null) {
                if (address.getId() == 0) {
                    AddressDAO adao = new AddressDAO();
                    adao.create(address);
                }
                statement.setInt(4, address.getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.setInt(5, c.getId());

            statement.executeUpdate();
        }

    }

    public void delete(Contact c) throws SQLException {
        if (c.getId() == 0) {
            return;
        }

        Connection connection = PersistenceManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {


            statement.setInt(1, c.getId());

            statement.executeUpdate();
        }
    }

    public Contact findById(int id) throws SQLException {
        if (id <= 0) {
            return null;
        }

        Connection connection = PersistenceManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {


            statement.setInt(1, id);

            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();
            if (!resultSet.next()) {
                resultSet.close();
                statement.close();
                return null;
            }
            Contact c = new Contact(resultSet.getString("con_email"),
                    resultSet.getString("con_first_name"),
                    resultSet.getString("con_last_name"),
                    null);//TODO : gerer les dependances d'adresse
            return c;
        }
    }
}
