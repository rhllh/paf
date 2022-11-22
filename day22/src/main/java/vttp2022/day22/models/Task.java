package vttp2022.day22.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    public enum Priority { LOW, MEDIUM, HIGH };
    
    private String username;
    private String password;
    private String task;
    private Priority priority;
    private Date completionDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setPriority(String priority) {
        this.priority = Priority.valueOf(priority);
    }

    public Date getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public void setCompletionDate(String completionDate) {
        try {
            this.completionDate = (new SimpleDateFormat("yyyyy-mm-dd")).parse(completionDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
