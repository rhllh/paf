package vttp2022.day23.repositories;

public class Queries {

    public static final String SQL_SELECT_STYLE =
        "select id, style_name from styles order by style_name asc";

    
    public static final String SQL_SELECT_STYLE_BREWERY = 
        "select s.style_name, br.name from styles s join beers be on s.id = be.style_id join breweries br on be.brewery_id = br.id where s.style_name = ?;";
}
