package vttp2022.paftest.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paftest.models.User;

import static vttp2022.paftest.repositories.Queries.*;

@Repository
public class UserRepo {
    
    @Autowired
    private JdbcTemplate template;

    public Optional<User> findUserByIdInRepo(String userId) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_USER_BY_ID, userId);

        final List<User> userList = new LinkedList<>();
        while (rs.next()) {
            userList.add(User.create(rs));
        }
        
        if (userList.size() > 0) {
            return Optional.of(userList.get(0));
        } else {
            return Optional.empty();
        }
            
    }

    public Integer insertUserInRepo(User user) {
        Integer inserted = template.update(SQL_INSERT_USER, user.getUserId(), user.getUsername(), user.getName() == null ? "" : user.getName());

        return inserted;
    }

    public Optional<User> findUserByUsernameInRepo(String username) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_USER_BY_USERNAME, username);

        final List<User> userList = new LinkedList<>();
        while (rs.next()) {
            userList.add(User.create(rs));
        }
        
        if (userList.size() > 0) {
            return Optional.of(userList.get(0));
        } else {
            return Optional.empty();
        }
    }
}
