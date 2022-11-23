package vttp2022.day23.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.day23.models.Brewery;

import static vttp2022.day23.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class BreweryRepository {

    @Autowired
    private JdbcTemplate template;

    public List<Brewery> getBrewery(String styleName) {
        System.out.println(styleName);

        final SqlRowSet rs = template.queryForRowSet(SQL_SELECT_STYLE_BREWERY, styleName);

        List<Brewery> br = new LinkedList<>();
        while (rs.next()) {
            br.add(Brewery.create(rs));
        }

        return br;
    }
}
