package vttp2022.paftest.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Task {

    private Integer taskId;
    private String description;
    private Integer priority;
    private Date dueDate;

    public Integer getTaskId() {
        return taskId;
    }
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPriority() {
        return priority;
    }
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public void setDueDate(String dueDateString) {
        try {
            this.dueDate = (new SimpleDateFormat("yyyy-mm-dd")).parse(dueDateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Task create(SqlRowSet rs) {
        Task t = new Task();
        t.setTaskId(rs.getInt("task_id"));
        t.setDescription(rs.getString("description"));
        t.setPriority(Integer.parseInt(rs.getString("priority")));
        t.setDueDate(rs.getDate("due_date"));

        return t;
    }
    
}
