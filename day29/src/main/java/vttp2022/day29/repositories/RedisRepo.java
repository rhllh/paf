package vttp2022.day29.repositories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.day29.models.Character;

@Repository
public class RedisRepo {
    
    @Autowired
    @Qualifier("marvelcache")
    private RedisTemplate<String, String> template;

    public void setKey(String name, List<Character> cList) {
        ValueOperations<String, String> ops = template.opsForValue();

        JsonArrayBuilder b = Json.createArrayBuilder();
        cList.stream().forEach(c -> b.add(c.toJSON()));
        ops.set(name, b.build().toString(), Duration.ofSeconds(300));
    }

    public Optional<List<Character>> get(String name) {
        ValueOperations<String, String> ops = template.opsForValue();

        String value = ops.get(name);
        if (null == value) {
            return Optional.empty();
        }

        List<Character> temp = new LinkedList<>();
        try (InputStream is = new ByteArrayInputStream(value.getBytes())) {
            JsonReader r = Json.createReader(is);
            temp = r.readArray().stream().map(v -> (JsonObject)v).map(v -> Character.fromCache(v)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(temp);
    }
}
