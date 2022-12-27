package vttp2022.paftest.controllers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import vttp2022.paftest.models.Task;
import vttp2022.paftest.models.User;
import vttp2022.paftest.services.TaskService;
import vttp2022.paftest.services.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private UserService userSvc;

    @Autowired
    private TaskService taskSvc;
    
    @GetMapping("/{username}")
    public String getUserTasks(@PathVariable("username") String username, Model model) {
        System.out.println("username > " + username);

        Optional<User> userOps = userSvc.findUserByUsername(username);

        User u = new User();
        if (userOps.isEmpty()) {
            System.out.println("user %s does not exist".formatted(username));
            u.setUsername(username);
            userSvc.insertUser(u);
        } else {
            System.out.println("user %s exists".formatted(username));
            u = userOps.get();
            u.setTaskList(taskSvc.getUserTasks(u.getUserId()));
        }

        model.addAttribute("user", u);
        model.addAttribute("username", username);

        return "user";
    }

    @PostMapping("/{username}/save")
    public String saveUserTasks(@PathVariable("username") String username, Model model, @RequestBody MultiValueMap<String, String> form) {

        System.out.println("username postmapping > " + username);

        Optional<User> userOps = userSvc.findUserByUsername(username);
        User u = userOps.get();
        System.out.println("user id > " + u.getUserId());
        
        List<Task> taskList = new LinkedList<>();
        OptionalInt sizeOfForm = form.values().stream().filter(Objects::nonNull).mapToInt(List::size).findAny();
    
        // for each item in map, create a task
        List<Map<String, String>> result = new LinkedList<>();
        sizeOfForm.ifPresent(size -> {
            for (int i = 0; i < size; i++) {
                int index = i;
                Map<String, String> task = new HashMap<>();
                form.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null)
                        .forEach(entry -> task.put(entry.getKey(), entry.getValue().get(index)));
                result.add(task);
            }
        });

        final ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < result.size(); i++) {
            Task t = mapper.convertValue(result.get(i), Task.class);
            taskList.add(t);
        }
        
        // upsert task
        try {
            taskSvc.upsertTask(taskList, u.getUserId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMsg", e.getMessage());
            return "error";
        }

        List<Task> taskListAfterUpsert = taskSvc.getUserTasks(u.getUserId());
        u.setTaskList(taskListAfterUpsert);

        model.addAttribute("user", u);
        model.addAttribute("username", username);

        return "saved";
    }

    @GetMapping("/{username}/delete/{taskId}")
    public String deleteTask(@PathVariable("username") String username, @PathVariable("taskId") String taskId,
                             Model model) {
        System.out.println("username > " + username);
        System.out.println("taskId > " + taskId);

        // delete task
        try {
            taskSvc.deleteTask(taskId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMsg", "%s. Task ID %s was not deleted successfully.".formatted(e.getMessage(),taskId));
            return "error";
        }

        Optional<User> userOps = userSvc.findUserByUsername(username);
        User u = userOps.get();
        List<Task> taskListAfterUpsert = taskSvc.getUserTasks(u.getUserId());
        u.setTaskList(taskListAfterUpsert);

        model.addAttribute("user", u);
        model.addAttribute("username", username);

        return "saved";
    }
}
