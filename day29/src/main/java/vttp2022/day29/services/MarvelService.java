package vttp2022.day29.services;

import java.io.StringReader;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.day29.models.Character;

@Service
public class MarvelService {
    
    // inject into service the private and public key
    @Value("${MARVEL_PUBLIC_KEY}")
    private String publicKey;

    @Value("${MARVEL_PRIVATE_KEY}")
    private String privateKey;

    private static final String URL = "https://gateway.marvel.com:443/v1/public/characters";

    public List<Character> searchCharByStartWith(String name) {

        

        Long ts = System.currentTimeMillis();
        String signature = "%d%s%s".formatted(ts, privateKey, publicKey);
        String hash = "";

        // message digest = md5, sha1, sha512
        try {
            // get an instance of MD5
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // update message digest
            md5.update(signature.getBytes());

            // calculate hash
            byte[] h = md5.digest();

            // stringify mds digest
            hash = HexFormat.of().formatHex(h);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * https://gateway.marvel.com:443/v1/public/characters
         * ?nameStartsWith=Sp&limit=10
         * &ts=
         * &apikey=
         * &hash=
         */
        String searchUrl = UriComponentsBuilder.fromUriString(URL)
            .queryParam("nameStartsWith", name)
            .queryParam("limit", 10)
            .queryParam("ts", ts)
            .queryParam("apikey", publicKey)
            .queryParam("hash", hash)
            .toUriString();

        System.out.println("url > " + searchUrl);

        RequestEntity<Void> req = RequestEntity.get(searchUrl).accept(MediaType.APPLICATION_JSON).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();
        
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject result = reader.readObject();
        JsonArray data = result.getJsonObject("data").getJsonArray("results");

        List<Character> cList = new LinkedList<>();
        for (Integer i = 0; i < data.size(); i++) {
            cList.add(Character.create(data.getJsonObject(i)));
        }

        return cList;
    }


}
