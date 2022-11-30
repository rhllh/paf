package vttp2022.day23.repositories;

public class Queries {

    public static final String SQL_SELECT_STYLE =
        "select id, style_name from styles order by style_name asc";
    
    public static final String SQL_SELECT_BREWERY_BY_STYLE = 
        """
        select distinct br.name
            from beers be
            join styles s
            on be.cat_id = s.cat_id
            join breweries br
            on be.brewery_id = br.id
            where s.id = ?
            order by br.name asc
        """;}
