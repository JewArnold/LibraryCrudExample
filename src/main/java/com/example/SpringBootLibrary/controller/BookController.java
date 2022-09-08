package com.example.SpringBootLibrary.controller;
import com.example.SpringBootLibrary.model.Book;
import com.example.SpringBootLibrary.model.Customer;
import com.example.SpringBootLibrary.services.BookService;
import com.example.SpringBootLibrary.services.CustomerService;
import com.example.SpringBootLibrary.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/books")
@Controller
public class BookController {
    private final BookValidator bookValidator;


    private final BookService bookService;
    private final CustomerService customerService;

    @Autowired
    public BookController(BookValidator bookValidator, BookService bookService, CustomerService customerService) {
        this.bookValidator = bookValidator;
        this.bookService = bookService;
        this.customerService = customerService;
    }


    @GetMapping()
    public String showAll(Model model,
                          @RequestParam(value = "page", required = false) Integer page,
                          @RequestParam(value = "books_per_page", required = false) Integer booksPerPage) {

        if (page == null || booksPerPage == null) {
            model.addAttribute("books", bookService.findAll("year"));
        } else {
            model.addAttribute("books", bookService.findAll(page, booksPerPage));
        }
        return "books/showAll";
    }


    @GetMapping("/{id}")
    public String showId(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("customer", new Customer());
        model.addAttribute("people", customerService.findAll());

        return "books/showId";
    }

    @GetMapping("/new")
    public String newCustomerPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findById(id));
        return "books/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/clear")
    public String release(@PathVariable("id") int id) {
        bookService.clearBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/set")
    public String assign(@PathVariable("id") int id, @ModelAttribute("customer") Customer customer) {
        bookService.setCustomer(id, customer);
        return "redirect:/books/" + id;
    }


    @GetMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute(new Book());
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.findByNamePart(query));
        return "books/search";
    }


}
