package vttp2022.day22ws.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.day22ws.models.RSVP;

import static vttp2022.day22ws.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class RSVPRepo {
    
    @Autowired
    private JdbcTemplate template;

    public List<RSVP> queryAllRSVPs() {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_RSVPS);

        final List<RSVP> rsvps = new LinkedList<>();
        while (rs.next()) {
            RSVP rsvp = RSVP.create(rs);
            rsvps.add(rsvp);
        }

        return rsvps;
    }

    public List<RSVP> queryRSVPByName(String name) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_RSVP_BY_NAME, "%%%s%%".formatted(name));

        final List<RSVP> rsvps = new LinkedList<>();
        while (rs.next()) {
            RSVP rsvp = RSVP.create(rs);
            rsvps.add(rsvp);
        }

        return rsvps;
    }

    public boolean insertRSVP(RSVP rsvp) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_RSVP_BY_EMAIL, rsvp.getEmail());

        if (rs.next()) {
            return updateRSVP(rsvp);
        } else {
            return template.update(SQL_INSERT_RSVP, rsvp.getName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments()) > 0;
        }

    }

    public RSVP queryRSVPByEmail(String email) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_RSVP_BY_EMAIL, email);

        final List<RSVP> rsvps = new LinkedList<>();
        while (rs.next()) {
            RSVP rsvp = RSVP.create(rs);
            rsvps.add(rsvp);
        }

        return rsvps.get(0);
    }

    public boolean updateRSVP(RSVP rsvp) {
        return template.update(SQL_UPDATE_RSVP_BY_EMAIL, rsvp.getName(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getEmail()) > 0;
    }

    public Integer queryRSVPCount() {
        SqlRowSet rs = template.queryForRowSet(SQL_COUNT_RSVPS);
        Integer count = 0;
        while (rs.next()) {
            count = rs.getInt("total");
        }

        return count;
    }
}
