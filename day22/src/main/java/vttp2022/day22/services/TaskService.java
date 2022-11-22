package vttp2022.day22.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.day22.models.Task;
import vttp2022.day22.repositories.TaskRepository;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepo;

    public boolean createTask(Task task) {
        int count = taskRepo.createTask(task);
        System.out.println("Task count > " + count);

        return count > 0;
    }
}
