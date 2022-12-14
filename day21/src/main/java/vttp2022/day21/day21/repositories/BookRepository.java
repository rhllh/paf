package vttp2022.day21.day21.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.day21.day21.models.Book;

import static vttp2022.day21.day21.repositories.Queries.*;

@Repository
public class BookRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<Book> getBooksByRating(Float rating) {

        // perform query
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_BOOKS_BY_RATING, rating);

        final List<Book> books = new LinkedList<>();

        // attempt to move cursor to next row
        while (rs.next()) {
            // we have a record
            Book b = Book.create(rs);
            books.add(b);
        }

        return books;
    }

    public List<Book> getBooksByTitle(String title) {

        //SqlRowSet rs = template.queryForRowSet(SQL_SELECT_BOOKS_BY_TITLE_AND_LIMIT, "%"+title+"%", numResult);
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_BOOKS_BY_TITLE, "%%%s%%".formatted(title));

        final List<Book> books = new LinkedList<>();

        while (rs.next()) {
            Book b = Book.create(rs);
            books.add(b);
        }

        return books;
    }

    public List<Book> getBooksByTitleAndOffset(String title, Integer offset) {

        SqlRowSet rs;
        if (offset == 0) {
            rs = template.queryForRowSet(SQL_SELECT_BOOKS_BY_TITLE, "%%%s%%".formatted(title));
        } else {
            rs = template.queryForRowSet(SQL_SELECT_BOOKS_BY_TITLE_AND_OFFSET, "%%%s%%".formatted(title), offset);
        }

        final List<Book> books = new LinkedList<>();
        while (rs.next()) {
            Book b = Book.create(rs);
            books.add(b);
        }

        return books;
    }
}
