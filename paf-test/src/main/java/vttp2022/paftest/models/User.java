package vttp2022.paftest.models;

import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class User {
    
    private String userId;
    private String username;
    private String name;
    private List<Task> taskList;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Task> getTaskList() {
        return taskList;
    }
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public static User create(SqlRowSet rs) {
        User u = new User();
        u.setUserId(rs.getString("user_id"));
        u.setUsername(rs.getString("username"));
        u.setName(rs.getString("name"));

        return u;
    }
    
}
