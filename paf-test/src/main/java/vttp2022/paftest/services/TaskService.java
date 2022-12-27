package vttp2022.paftest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.paftest.models.Task;
import vttp2022.paftest.repositories.TaskRepo;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepo taskRepo;

    public List<Task> getUserTasks(String userId) {
        Optional<List<Task>> taskListOps = taskRepo.getUserTasksFromRepo(userId);

        return taskListOps.get();
    }

    @Transactional(rollbackFor = TaskException.class)
    public void upsertTask(List<Task> taskList, String userId) throws TaskException {
        for (Task t : taskList) {
            if (!taskRepo.checkIfTaskExists(t)) {
                taskRepo.insertTaskNotExist(t, userId);
            } else {
                taskRepo.insertTaskExist(t, userId);
            }
        }
    }

    @Transactional(rollbackFor = TaskException.class)
    public void deleteTask(String taskId) throws TaskException {
        taskRepo.deleteTask(taskId);
    }
}
