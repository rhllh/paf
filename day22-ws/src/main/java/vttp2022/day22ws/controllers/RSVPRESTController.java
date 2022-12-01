package vttp2022.day22ws.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.day22ws.models.RSVP;
import vttp2022.day22ws.repositories.RSVPRepo;

@RestController
@RequestMapping(path="/api")
public class RSVPRESTController {

    @Autowired
    private RSVPRepo rsvpRepo;
    
    // get all RSVPs
    @GetMapping(path="/rsvps",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRSVPs() {
        List<RSVP> rsvps = rsvpRepo.queryAllRSVPs();

        JsonArrayBuilder jb = Json.createArrayBuilder();
        for (RSVP r : rsvps) {
            jb.add(r.toJSON());
        }
        JsonArray result = jb.build();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    // get RSVP by name
    @GetMapping(path="/rsvp",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchRSVPByName(@RequestParam(value="q", required=true) String name) {
        List<RSVP> rsvps = rsvpRepo.queryRSVPByName(name);

        if (rsvps.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No RSVP found with name like %s".formatted(name));
        }

        JsonArrayBuilder jb = Json.createArrayBuilder();
        for (RSVP r : rsvps) {
            jb.add(r.toJSON());
        }
        JsonArray result = jb.build();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    // create RSVP
    @PostMapping(path="/rsvp",consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRSVP(@RequestBody String json) {

        RSVP r = new RSVP();
        try {
            r = RSVP.create(json);

            rsvpRepo.insertRSVP(r);

            return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("RSVP created for %s".formatted(r.getName()));
        } catch (Exception e) {
            JsonObject response = Json.createObjectBuilder()
                                    .add("error", e.getMessage())
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }
    
    }

    // update existing RSVP by email
    @PutMapping(path="/rsvp/{email}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putRSVP(@PathVariable("email") String email, @RequestBody String json) {
        RSVP r = new RSVP();
        try {
            r = RSVP.create(json);
        } catch (Exception e) {
            JsonObject response = Json.createObjectBuilder()
                                    .add("error", e.getMessage())
                                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }

        boolean result = false;
        try {
            result = rsvpRepo.updateRSVP(r);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No RSVP found for email %s".formatted(r.getEmail()));
        }
        
        JsonObject response = Json.createObjectBuilder()
                                .add("updated", result)
                                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());
    }

    // get total number of RSVPs
    @GetMapping("/rsvps/count")
    public ResponseEntity<String> getRSVPCount() {
        Integer count = rsvpRepo.queryRSVPCount();

        JsonObject response = Json.createObjectBuilder()
                                .add("total", count)
                                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response.toString());
    }
}
