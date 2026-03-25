package by.strukov.library.dao;

import by.strukov.library.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        String sqlQuery = "SELECT * FROM people";
        List<Person> people = jdbcTemplate.query(sqlQuery, new BeanPropertyRowMapper<>(Person.class));

        return people.stream().sorted().toList();
    }

    public void create(Person newPerson) {
        String sqlQuery = "INSERT INTO people (full_name, birth_date) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, newPerson.getFullName(), newPerson.getBirthDate());
    }

    public void update(int personID, Person updatedPerson) {
        String sqlQuery = "UPDATE people SET full_name = ?, birth_date = ? where person_id = ?";
        jdbcTemplate.update(sqlQuery, updatedPerson.getFullName(), updatedPerson.getBirthDate(), personID);
    }

    public Person readByID(int personID) {
        String sqlQuery = "SELECT * FROM people WHERE person_id = ?";
        List<Person> personList =
                jdbcTemplate.query(sqlQuery, new Object[]{personID}, new BeanPropertyRowMapper<>(Person.class));
        return personList.stream().findFirst().orElse(null);
    }

    public void delete(int person_id) {
        String sqlQuery = "DELETE FROM people WHERE person_id = ?";
        jdbcTemplate.update(sqlQuery, person_id);
    }
}
