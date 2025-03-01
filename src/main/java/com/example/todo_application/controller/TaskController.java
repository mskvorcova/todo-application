package com.example.todo_application.controller;

import com.example.todo_application.services.MarkdownService;
import com.example.todo_application.model.Task;
import com.example.todo_application.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private final MarkdownService markdownService;

    public TaskController(MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

    @GetMapping
    public String showTasks(@RequestParam(value = "selectedId", required = false) Long selectedId, Model model) {
        List<Task> tasks = tasksRepository.findAll();
        Collections.reverse(tasks);
        model.addAttribute("tasks", tasks);

        Task selectedTask = (selectedId != null) ? tasksRepository.findById(selectedId).orElse(null) :
                (tasks.isEmpty() ? null : tasks.get(0));

        if (selectedTask != null) {
            System.out.println(selectedTask.getDescription());
            String markdownDescription = markdownService.convertToHtml(selectedTask.getDescription() == null ? " " : selectedTask.getDescription());
            model.addAttribute("selectedTaskDescription", markdownDescription);
        }

        model.addAttribute("selectedTask", selectedTask);

        return "tasks";
    }

    @PostMapping("/create")
    public String createTask(@RequestParam String name,
                             @RequestParam(required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        Task task = new Task();
        task.setName(name);
        task.setDescription(" ");
        task.setDueDate(dueDate);
        tasksRepository.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/toggle")
    public String toggleTaskCompletion(@RequestParam Long id) {
        Task task = tasksRepository.findById(id).orElseThrow();
        task.setState(!task.getState());
        tasksRepository.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long id) {
        tasksRepository.deleteById(id);
        return "redirect:/tasks";
    }

    @PostMapping("/description")
    public String showDescription(@RequestParam Long id, Model model) {
        model.addAttribute("description", Objects.requireNonNull(tasksRepository.findById(id).orElse(null)).getDescription());
        return "description";
    }

    @PostMapping("/edit")
    public String editTask(@RequestParam Long id,
                           @RequestParam String name,
                           @RequestParam String description) {
        Task task = tasksRepository.findById(id).orElseThrow();
        task.setName(name);
        task.setDescription(description);
        tasksRepository.save(task);
        return "redirect:/tasks";
    }

    @PostMapping("/redirectNotes")
    public String redirectNotes() {
        return "redirect:/notes";
    }

    @PostMapping("/redirectTasks")
    public String redirectTasks() {
        return "redirect:/tasks";
    }
}
