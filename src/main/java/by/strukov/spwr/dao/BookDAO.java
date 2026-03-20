package by.strukov.spwr.dao;

import by.strukov.spwr.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getListAllBooks() {
        String sqlQuery = "SELECT * FROM books";
        return jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Book.class));
    }
}
