package ru.gb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.service.IssueService;

@Controller
@RequestMapping("/ui")
public class IssueControllerUI {
    private final IssueService service;

    public IssueControllerUI(IssueService service) {
        this.service = service;
    }

    @GetMapping("/issues")
    public String getAllIssues(Model model) {
        model.addAttribute("issues", service.getAllIssuesToStringArray());
        return "issues";
    }
}
