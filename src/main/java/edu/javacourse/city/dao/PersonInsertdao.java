package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.exception.PersonCheckException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonInsertdao {
    //SQL_CHECK_EXISTENCE = "SELECT COUNT(*)\n" +
    //            "FROM cr_address_person ap\n" +
    //            "INNER JOIN cr_person p ON p.person_id = ap.person_id \n" +
    //            "INNER JOIN cr_address a ON a.address_id = ap.address_id\n" +
    //            "WHERE\n" +
    //            "  UPPER(p.sur_name) = UPPER(?)\n" +
    //            "  AND UPPER(p.given_name) = UPPER(?)\n" +
    //            "  AND UPPER(p.patronymic) = UPPER(?)\n" +
    //            "  AND p.date_of_birth = ?\n" +
    //            "  AND a.street_code = ?\n" +
    //            "  AND UPPER(a.building) = UPPER(?)\n" +
    //            "  AND UPPER(a.extension) = UPPER(?)\n" +
    //            "  AND UPPER(a.apartment) = UPPER(?)\n";
    private static final String SQL_CHECK_EXISTENCE = "select COUNT(*) from cr_person where email = ?";
    private static final String SQL_INSERT = "INSERT INTO cr_person (email)\n" +
            "SELECT  ?";
    private ConnectionBuilder connectionBuilder;
    public PersonInsertdao() {
    }
    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }
    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
    public PersonResponse insertPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();
        try (Connection con = getConnection();
             PreparedStatement checkExistenceStmt = con.prepareStatement(SQL_CHECK_EXISTENCE);
             PreparedStatement insertStmt = con.prepareStatement(SQL_INSERT)) {


            insertStmt.setString(1, request.getEmail());
            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Record successfully added to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersonCheckException("Error occurred while inserting person: " + e.getMessage());
        }
        return response;
    }
}
