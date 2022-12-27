package vttp2022.paftest.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paftest.models.Task;

import static vttp2022.paftest.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepo {

    @Autowired
    private JdbcTemplate template;

    public Optional<List<Task>> getUserTasksFromRepo(String userId) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_TASKS_BY_USERID, userId);

        final List<Task> taskList = new LinkedList<>();
        while (rs.next()) {
            taskList.add(Task.create(rs));
        }

        return Optional.of(taskList);
    }

    public boolean insertTaskNotExist(Task task, String userId) {
        int inserted = template.update(SQL_INSERT_TASK, task.getDescription(), task.getPriority(), task.getDueDate(), userId);

        return inserted > 0;
    }

    public boolean insertTaskExist(Task task, String userId) {
        int updated = template.update(SQL_UPDATE_TASK, task.getDescription(), task.getPriority(), task.getDueDate(), userId, task.getTaskId());

        return updated > 0;
    }

    public boolean checkIfTaskExists(Task task) {
        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_TASKS_BY_TASKID, task.getTaskId());

        return rs.next();
    }

    public boolean deleteTask(String taskId) {
        return template.update(SQL_DELETE_TASK_BY_TASKID, taskId) > 0;
    }
    
}
