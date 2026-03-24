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

    public List<Book> getListPersonsBooks(int personID) {
        String sqlQuery = "SELECT books.title, books.author, books.year " +
                "FROM books INNER JOIN people on people.person_id = books.fk_owner_id " +
                "WHERE person_id = ?";
        List<Book> books = jdbcTemplate.query(sqlQuery, new Object[]{personID}, new BeanPropertyRowMapper<>(Book.class));
        return books;
    }

    public void create(Book newBook) {
        String sqlQuery = "INSERT INTO books (title, author, year) values (?,?,?)";
        jdbcTemplate.update(sqlQuery, newBook.getTitle(), newBook.getAuthor(), newBook.getYear());
    }

    public Book show(int id) {
        String sqlQuery = "SELECT * FROM books WHERE book_id = ?";
        List<Book> bookList = jdbcTemplate.query(sqlQuery, new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
        return bookList.stream().findAny().orElse(null);
    }

    public void update(Book updatedBook) {
        String sqlQuery = "UPDATE books set title = ?, author = ?, year = ? where book_id = ?";
        jdbcTemplate.update(sqlQuery,
                updatedBook.getTitle(),
                updatedBook.getAuthor(),
                updatedBook.getYear(),
                updatedBook.getBook_id());
    }

    public void setBookOwner(Integer bookID, Integer ownerID) {
        String sqlQuery = "UPDATE books SET fk_owner_id = ? WHERE book_id = ?";
        jdbcTemplate.update(sqlQuery, ownerID, bookID);
    }

    public void delete(int id) {
        String sqlQuery = "DELETE FROM books WHERE book_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    public boolean isFullEquals(Book book) {
        String sqlQuery = "SELECT * FROM books WHERE title = ? and author = ? and year = ?";
        List<Book> books = jdbcTemplate.query(sqlQuery,
                new Object[]{book.getTitle(), book.getAuthor(), book.getYear()},
                new BeanPropertyRowMapper<>(Book.class));
        return !books.isEmpty();
    }
}
