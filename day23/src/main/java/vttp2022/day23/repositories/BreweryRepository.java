package vttp2022.day23.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<Brewery> getBreweriesSql(Integer styleId) {

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SELECT_BREWERY_BY_STYLE, styleId);

        List<Brewery> br = new LinkedList<>();
        while (rs.next()) {
            br.add(Brewery.create(rs));
        }

        return br;
    }

    public List<Brewery> getBreweriesRedis(Integer styleId) {
        // check if key exists in redis
        List<Brewery> br = new LinkedList<>();
        if (redisTemplate.hasKey(String.valueOf(styleId))) {
            System.out.println("here");
            br = (List<Brewery>) redisTemplate.opsForValue().get(String.valueOf(styleId));
        } else {
            br = getBreweriesSql(styleId);
            redisTemplate.opsForValue().set(String.valueOf(styleId), br);
        }
        
        return br;
    }
}
