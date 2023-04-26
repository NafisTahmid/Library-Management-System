package com.application.courselibrary.controller;

import com.application.courselibrary.entity.Publisher;
import com.application.courselibrary.service.AuthorService;
import com.application.courselibrary.service.BookService;
import com.application.courselibrary.service.CategoryService;
import com.application.courselibrary.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PublisherController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/publishers")
    public String findAllPublishers(Model model) {
        List<Publisher> publisher = publisherService.findAllPublishers();
        model.addAttribute("publishers", publisher);
        return "publishers";
    }

    @GetMapping("remove-publisher/{id}")
    public String deletePublisher(@PathVariable Long id, Model model) {
        publisherService.deletePublisher(id);
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "publishers";
    }

    @GetMapping("/update-publisher/{id}")
    public String updatePublisher(@PathVariable Long id, Model model){
        Publisher publisher = publisherService.findPublisherById(id);
        model.addAttribute("publisher", publisherService.findPublisherById(id));
        return "update-publisher";

    }

    @PostMapping("/update-publisher/{id}")
    public String updatePublisher(@PathVariable Long id, Publisher publisher, BindingResult result, Model model){

        if(result.hasErrors()){
            return "update-publisher";
        }
        publisherService.updatePublisher(publisher);
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }


    @GetMapping("/add-publisher")
    public String addPublisher(Publisher publisher, Model model){
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("books", bookService.findAllBooks());
        model.addAttribute("authors", authorService.findAllAuthors());
        return "add-publisher";

    }
    @PostMapping("/save-publisher")
    public String savePublisher(Publisher publisher, BindingResult result, Model model){

        if(result.hasErrors()){
            return "update-publisher";
        }
        publisherService.createPublisher(publisher);
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }

}
