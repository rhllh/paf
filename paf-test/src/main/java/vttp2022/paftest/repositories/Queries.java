package vttp2022.paftest.repositories;

public class Queries {
    
    public static final String SQL_SELECT_USER_BY_ID = "select * from user where user_id = ?";

    public static final String SQL_INSERT_USER = "insert into user(user_id, username, name) values(?, ?, ?)";

    public static final String SQL_SELECT_USER_BY_USERNAME = "select * from user where username = ?";

    public static final String SQL_SELECT_TASKS_BY_USERID = "select * from task where user_id = ?";

    public static final String SQL_INSERT_TASK = "insert into task(description, priority, due_date, user_id) values(?, ?, ?, ?)";

    public static final String SQL_UPDATE_TASK = "update task set description = ?, priority = ?, due_date = ?, user_id = ? where task_id = ?";

    public static final String SQL_SELECT_TASKS_BY_TASKID = "select * from task where task_id = ?";

    public static final String SQL_DELETE_TASK_BY_TASKID = "delete from task where task_id = ?";
}
