package vttp2022.day22.models;

public class Task {
    
    private String username;
    private String password;
    private String task;
    private String priority;
    private String completionDate;

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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    
}
