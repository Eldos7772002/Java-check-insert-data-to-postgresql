package edu.javacourse.city.web;

import edu.javacourse.city.dao.PersonInsertdao;
import edu.javacourse.city.dao.PoolConnectionBuilder;
import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.dao.PersonInsertdao;
import edu.javacourse.city.dao.PoolConnectionBuilder;
import edu.javacourse.city.domain.PersonRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "InsertNameServlet", urlPatterns = {"/insertPerson"})
public class InsertNameServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(InsertNameServlet.class);

    private PersonInsertdao dao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");



        try {
            PersonRequest pr = new PersonRequest();

            pr.setEmail(req.getParameter("email"));
            PersonInsertdao dao = new PersonInsertdao();
            dao.setConnectionBuilder(new PoolConnectionBuilder());
            dao.insertPerson(pr);
            resp.getWriter().write("Data successfully inserted into the database!");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.getWriter().write("Error occurred while inserting data into the database!");
        }
    }
}
