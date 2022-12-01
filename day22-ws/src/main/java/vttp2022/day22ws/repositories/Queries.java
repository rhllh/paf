package vttp2022.day22ws.repositories;

public class Queries {
    
    public static final String SQL_SELECT_RSVPS = "select * from rsvp";

    public static final String SQL_SELECT_RSVP_BY_NAME = "select * from rsvp where name like ?";

    public static final String SQL_SELECT_RSVP_BY_EMAIL = "select * from rsvp where email like ?";
    
    public static final String SQL_INSERT_RSVP = 
        "insert into rsvp(name, email, phone, confirmation_date, comments)" +
        "values(?, ?, ?, ?, ?)";

    public static final String SQL_UPDATE_RSVP_BY_EMAIL = 
        "update rsvp set name = ?, phone = ? , confirmation_date = ?, comments = ? where email = ?";

    public static final String SQL_COUNT_RSVPS = 
        "select count(*) as total from rsvp";
}
