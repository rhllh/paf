package vttp2022.day23.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.day23.models.Style;
import static vttp2022.day23.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class StyleRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<Style> getStyle() {

        final SqlRowSet rs = template.queryForRowSet(SQL_SELECT_STYLE);

        List<Style> styleList = new LinkedList<>();
        while (rs.next()) {
            styleList.add(Style.create(rs));
        }

        return styleList;
    }
}
