package com.example.todo_application.controller;

import com.example.todo_application.model.Note;
import com.example.todo_application.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {
    @Autowired
    NotesRepository notesRepository;
    @GetMapping
    public String showNotes(Model model) {
        List<Note> noteList = notesRepository.findAll();
        model.addAttribute("noteList", noteList);
        return "notes";
    }

    @PostMapping("/redirectNotes")
    public String redirectNotes() {
        return "redirect:/notes";
    }

    @PostMapping("/redirectTasks")
    public String redirectTasks() {
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable Long id, Model model) {
        Note note = notesRepository.findById(id).orElse(null);
        if (note != null) {
            model.addAttribute("note", note);
            return "editNote"; // имя страницы для редактирования (например, editNote.html)
        } else {
            return "redirect:/notes"; // если заметки не существует, перенаправить обратно на список
        }
    }


    @PostMapping("/edit/{id}")
    public String editNote(@PathVariable Long id,
                           @RequestParam String title,
                           @RequestParam String content) {
        Note note = notesRepository.findById(id).orElseThrow();
        note.setTitle(title);
        note.setContent(content);
        notesRepository.save(note);
        return "redirect:/notes";
    }

    @PostMapping("/create")
    public String createNote() {
        Note newNote = new Note();
        newNote.setTitle("");
        newNote.setContent("");
        notesRepository.save(newNote);
        return "redirect:/notes/edit/" + newNote.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        notesRepository.deleteById(id);
        return "redirect:/notes";
    }
}
