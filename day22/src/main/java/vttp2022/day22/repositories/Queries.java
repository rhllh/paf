package vttp2022.day22.repositories;

public class Queries {
    
    public static final String SQL_INSERT_USER = "insert into user(username, password, email, phone, dob)" +
                "values(?, sha(?), ?, ?, ?)";

    public static final String SQL_VALIDATE_LOGIN = "select count(*) > 0 as valid_cred from user where username = ? and password = sha(?)";

    public static final String SQL_INSERT_TASK = "insert into task(task_name, priority, assign_to, completion_date)" + 
                "values(?, ?, ?, ?)";
}
