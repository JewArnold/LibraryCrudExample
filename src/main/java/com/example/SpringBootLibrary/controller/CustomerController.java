package com.example.SpringBootLibrary.controller;

import com.example.SpringBootLibrary.model.Customer;
import com.example.SpringBootLibrary.services.CustomerService;
import com.example.SpringBootLibrary.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/customers")


public class CustomerController {


    private final CustomerValidator customerValidator;



    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerValidator customerValidator, CustomerService customerService) {
        this.customerValidator = customerValidator;
        this.customerService = customerService;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers/showAll";
    }


    @GetMapping("/{id}")
    public String showId(@PathVariable("id") int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        model.addAttribute("list", customerService.bookList(id));
        return "customers/showId";
    }

    @GetMapping("/new")
    public String newCustomerPage(@ModelAttribute("customer") Customer customer) {
        return "customers/new";
    }


    @PostMapping()
    public String create(@ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult) {
        customerValidator.validate(customer, bindingResult);

        if (bindingResult.hasErrors())
            return "customers/new";

        customerService.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("customer", customerService.findById(id));
        return "customers/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        customerValidator.validate(customer, bindingResult);

        if (bindingResult.hasErrors())
            return "customers/edit";

        customerService.update(id, customer);
        return "redirect:/customers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        customerService.delete(id);
        return "redirect:/customers";
    }


}
