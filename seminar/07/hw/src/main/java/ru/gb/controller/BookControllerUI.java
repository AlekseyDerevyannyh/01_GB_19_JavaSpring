package ru.gb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.service.BookService;

@Controller
@RequestMapping("/ui")
public class BookControllerUI {
    private final BookService service;

    public BookControllerUI(BookService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", service.getAllBooks());
        return "books";
    }
}
