package dk.goodmanservice.goodmanservice.Repository;

import dk.goodmanservice.goodmanservice.Model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;

/**
 * Lavet af Markus
 */

@Repository
@Component("AR")
public class AppointmentRepository implements IRepository<Appointment> {

    private PreparedStatement preparedStatement;
    private String sql;

    @Autowired
    private DBConnect db;

    /**
     * AppointmentRepository står for al kontakt med databasen med alt der omhandler Appointments,
     * opbygningen er standard CRUD som kommer fra vores Interface.
     */

    @Override
    public void create(Appointment obj) throws SQLException {
        sql = "INSERT INTO appointments (description, date, time, fk_employee, fk_customer) " +
                "VALUES (?, ?, ?, ?, ?)";

            preparedStatement = db.getConnection().prepareStatement(sql);
            executeAppointment(obj);
    }

    @Override
    public void edit(Appointment obj) throws SQLException {
        sql = "UPDATE appointments " +
                "SET description = ?, date = ?, time=?, fk_employee = ?, fk_customer = ? " +
                "WHERE id = '" + obj.getId() + "'";

            preparedStatement = db.getConnection().prepareStatement(sql);
            executeAppointment(obj);
    }

    private void executeAppointment(Appointment obj) throws SQLException {
        preparedStatement.setString(1, obj.getDescription());
        preparedStatement.setString(2, obj.getDate());
        preparedStatement.setString(3, obj.getTime());
        preparedStatement.setInt(4, obj.getEmployeeId());
        preparedStatement.setInt(5, obj.getCustomerId());
        preparedStatement.execute();
    }

    @Override
    public void delete(int id) throws SQLException {
        sql = "DELETE FROM appointments " +
                "WHERE id = ?";

            preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
    }

    /**
     * option bliver her brugt til kun at hente det data ud for den bruger der er logget ind
     */

    @Override
    public ResultSet fetch(String option) throws SQLException {
        sql = "SELECT appointments.*, u1.firstName AS eFirstName, u1.lastName AS eLastName, u2.firstName AS cFirstName, u2.lastName AS cLastName " +
                "FROM appointments " +
                "LEFT JOIN users as u1 ON appointments.fk_employee = u1.id " +
                "LEFT JOIN users as u2 ON appointments.fk_customer = u2.id";

        if(!option.equals("all")) {
            sql += " WHERE appointments.fk_employee = '" + option + "'";
        }

        sql += " ORDER BY appointments.date";

        preparedStatement = db.getConnection().prepareStatement(sql);
            return preparedStatement.executeQuery();
    }

    @Override
    public ResultSet findById(int id) throws SQLException {
        sql = "SELECT * FROM appointments " +
                "WHERE id = ?";

            preparedStatement = db.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
    }
}
