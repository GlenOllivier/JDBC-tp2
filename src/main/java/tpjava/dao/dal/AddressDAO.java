package tpjava.dao.dal;

import tpjava.dao.domain.Address;

import java.sql.*;

public class AddressDAO {

    private static final String SELECT_QUERY = "SELECT * FROM t_address_add WHERE add_id = ?;";
    private static final String INSERT_QUERY = "INSERT INTO t_address_add (add_number, add_street, add_city, add_zipcode, add_country) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY = "UPDATE t_address_add SET add_number = ?, add_street = ?, add_city = ?, add_zipcode = ?, add_country = ? WHERE add_id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM t_address_add WHERE add_id = ?;";


    public void create(Address a) throws SQLException {

        Connection connection = PersistenceManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, a.getNumber());
            statement.setString(2, a.getStreet());
            statement.setString(3, a.getCity());
            statement.setString(4, a.getZipcode());
            statement.setString(5, a.getCountry());

            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            a.setId(resultSet.getInt(1));
            resultSet.close();
        }
    }

    //TODO : methods
    public void update(Address a) throws SQLException {
        if (a.getId() == 0) {
            create(a);
            return;
        }
        Connection connection = PersistenceManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setString(1, a.getNumber());
            statement.setString(2, a.getStreet());
            statement.setString(3, a.getCity());
            statement.setString(4, a.getZipcode());
            statement.setString(5, a.getCountry());
            statement.setInt(6, a.getId());

            statement.executeUpdate();
        }
    }

    public void delete(Address a) throws SQLException {
        if (a.getId() == 0) {
            return;
        }
        Connection connection = PersistenceManager.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

            statement.setInt(1, a.getId());

            statement.executeUpdate();
        }
    }

    public Address findById(int id) throws SQLException {
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
            Address a = new Address(resultSet.getString("add_number"),
                    resultSet.getString("add_street"),
                    resultSet.getString("add_city"),
                    resultSet.getString("add_zipcode"),
                    resultSet.getString("add_country")
            );

            a.setId(resultSet.getInt("add_id"));
            //TODO : gerer les contacts liés
            return a;
        }
    }
}
