package dk.goodmanservice.goodmanservice.Repository;
import dk.goodmanservice.goodmanservice.Model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@Component("UR")
public class UserRepository implements IRepository<User> {

    private Connection con;
    private PreparedStatement preparedStatement;
    private String sql;

    public UserRepository() {
        try {
            this.con = DriverManager.getConnection(
                    "jdbc:mysql://den1.mysql5.gear.host/goodmanservicedb",
                    "goodmanservicedb",
                    "Ly02_scr-4ds");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(User obj) throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, email, address, phone, fk_role, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = con.prepareStatement(sql);
        executeUser(sql, obj);
    }

    @Override
    public void edit(User obj) throws SQLException {
        sql = "UPDATE users SET first_name=?, last_name=?, email=?, address=?, phone=?, fk_role=?, password=? WHERE id = '" + obj.getId() + "'";
        preparedStatement = con.prepareStatement(sql);
        executeUser(sql, obj);
    }

    private void executeUser(String sql, User obj) throws SQLException {
            preparedStatement.setString(1, obj.getFirstName());
            preparedStatement.setString(2, obj.getLastName());
            preparedStatement.setString(3, obj.getEmail());
            preparedStatement.setString(4, obj.getAddress());
            preparedStatement.setString(5, obj.getPhoneNumber());
            preparedStatement.setInt(6, obj.getRoleId());
            preparedStatement.setString(7, obj.getPassword());
            preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM users WHERE id=?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
    }

    @Override
    public ResultSet fetch(String option, int id) throws SQLException {
        switch (option) {
            case "all":
                sql = "SELECT * FROM users";
                break;
            case "employees":
                sql = "SELECT * FROM users WHERE fk_role=3";
                break;
            case "customers":
                sql = "SELECT * FROM users WHERE fk_role=4";
                break;
            case "boss":
                sql = "SELECT * FROM users WHERE fk_role=1";
                break;
            case "manager":
                sql = "SELECT * FROM users WHERE fk_role=4";
                break;
        }

            preparedStatement = con.prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT * FROM users WHERE id=?";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
