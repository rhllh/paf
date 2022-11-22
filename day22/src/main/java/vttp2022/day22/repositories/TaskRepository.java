package vttp2022.day22.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp2022.day22.models.Task;
import static vttp2022.day22.repositories.Queries.*;

@Repository
public class TaskRepository {
    
    @Autowired
    private JdbcTemplate template;

    public Integer createTask(Task task) {
        return template.update(SQL_INSERT_TASK, task.getTask(), task.getPriority().toString(), task.getUsername(), task.getCompletionDate());
    }
}
