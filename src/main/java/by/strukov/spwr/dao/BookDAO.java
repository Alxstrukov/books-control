package by.strukov.spwr.dao;

import by.strukov.spwr.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    public void create(Book newBook) {
        String sqlQuery = "INSERT INTO books (title, author, year) values (?,?,?)";
        jdbcTemplate.update(sqlQuery, newBook.getTitle(), newBook.getAuthor(), newBook.getYear());
    }

    public Book show(Long id) {
        String sqlQuery = "SELECT * FROM books WHERE id = ?";
        List<Book> bookList = jdbcTemplate.query(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        return bookList.stream().findAny().orElse(null);
    }

    public void update(Long id, Book updatedBook) {
        String sqlQuery = "UPDATE books set title = ?, author = ?, year = ? where book_id = ?";
        jdbcTemplate.update(sqlQuery, updatedBook.getTitle(), updatedBook.getTitle(), updatedBook.getYear(), id);
    }

    public Optional<Book> show(String title) {
        String sqlQuery = "SELECT * FROM books WHERE title = ?";
        return jdbcTemplate.query(sqlQuery,
                new Object[]{title},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void delete(Long id) {
        String sqlQuery = "DELETE FROM books WHERE id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }
}
