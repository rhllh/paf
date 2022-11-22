package vttp2022.day22.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.day22.models.User;
import static vttp2022.day22.repositories.Queries.*;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate template;

    public Integer createUser(User user) throws Exception {
        
        return template.update(SQL_INSERT_USER, user.getUsername(), 
                user.getPassword(), user.getEmail(), user.getPhone(), user.getDob());
    }

    public Integer validateUser(User user) throws Exception {

        return template.queryForObject(SQL_VALIDATE_LOGIN, Integer.class, new Object[] { user.getUsername(), user.getPassword() });
    }
}
