package ru.gb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.service.ReaderService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/ui")
public class ReaderControllerUI {
    private final ReaderService service;

    public ReaderControllerUI(ReaderService service) {
        this.service = service;
    }

    @GetMapping("/reader")
    public String getAllReaders(Model model) {
        model.addAttribute("readers", service.getAllReaders());
        return "readers";
    }

    @GetMapping("/reader/{id}")
    public String getReaderBooks(@PathVariable long id, Model model) {
        try {
            model.addAttribute("reader", service.getReaderById(id));
        } catch (NoSuchElementException e) {
            model.addAttribute("reader", null);
        }
        model.addAttribute("books", service.getBooksByReaderId(id));
        return "reader";
    }
}
