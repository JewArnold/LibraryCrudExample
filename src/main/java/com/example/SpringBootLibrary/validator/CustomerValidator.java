package com.example.SpringBootLibrary.validator;
import com.example.SpringBootLibrary.model.Customer;
import com.example.SpringBootLibrary.repositories.CustomerRepository;
import com.example.SpringBootLibrary.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerValidator implements Validator {
    private final CustomerService customerService;

    @Autowired
    public CustomerValidator(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;
        if (customerService.findByName(customer.getName()).isPresent()){
            errors.rejectValue("name","", "Данный человек уже числится в базе данных");
        }
        if (customer.getBirthYear() < 1901 || customer.getBirthYear()> 2022){
            errors.rejectValue("birthYear", "", "Возраст должен находиться в диапазоне 1901 и 2022");
        }
        if(customer.getName() == null||customer.getName().equals("")){
            errors.rejectValue("name","", "Имя не может быть пустым");
        }
        if (customer.getName().length() <2 ||customer.getName().length() >=80){
            errors.rejectValue("name","", "Мин.длина - 2, макс.длина - 80");
        }


    }
}
