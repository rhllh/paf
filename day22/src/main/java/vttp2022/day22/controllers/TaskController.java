package vttp2022.day22.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp2022.day22.models.Task;
import vttp2022.day22.models.User;
import vttp2022.day22.services.TaskService;
import vttp2022.day22.services.UserService;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private UserService userSvc;

    @Autowired
    private TaskService taskSvc;
    
    @GetMapping
    public String taskFormPage() {
        return "task";
    }

    @PostMapping("/created")
    public String postTask(@RequestBody MultiValueMap<String, String> form, Model model) {

        String username = form.getFirst("username");
        String password = form.getFirst("password");
        String task = form.getFirst("task");
        String priority = form.getFirst("priority");
        String completionDate = form.getFirst("date");

        System.out.printf("username: %s, password: %s, task: %s, priority: %s, completionDate: %s",
                     username, password, task, priority, completionDate);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            if (!userSvc.validateCred(user)) {
                System.out.println(">>>> error! user not found");
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }
        
        System.out.println("user " + username + " found");

        Task taskObj = new Task();
        taskObj.setUsername(username);
        taskObj.setPassword(password);
        taskObj.setTask(task);
        taskObj.setPriority(priority);
        taskObj.setCompletionDate(completionDate);

        try {
            if (!taskSvc.createTask(taskObj))
                System.out.println(">> error! task not created");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }

        model.addAttribute("username", username);
        model.addAttribute("priority", priority);
        model.addAttribute("completionDate", completionDate);
        model.addAttribute("task", task);

        return "task-created";
    }
}
