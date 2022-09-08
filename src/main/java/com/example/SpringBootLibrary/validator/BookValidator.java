package com.example.SpringBootLibrary.validator;
import com.example.SpringBootLibrary.model.Book;
import com.example.SpringBootLibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (bookService.findByName(book.getName()).isPresent()) {
            errors.rejectValue("name", "", "Данная книга уже находится в базе данных");
        }
        if (book.getYear() < 1700 || book.getYear() > 2022) {
            errors.rejectValue("year", "", "Год выпуска книги должен находиться в диапазоне 1700 и 2022");
        }
        if (book.getName() == null || book.getName().equals("")) {
            errors.rejectValue("name", "", "Название не может быть пустым");
        }
        if (book.getName().length() < 2 || book.getName().length() >= 80) {
            errors.rejectValue("name", "", "Мин.длина - 2, макс.длина - 80");
        }
        if (book.getAuthor() == null || book.getAuthor().equals("")) {
            errors.rejectValue("author", "", "Имя автора не может быть пустым");
        }
        if (book.getAuthor().length() < 2 || book.getAuthor().length() >= 80) {
            errors.rejectValue("author", "", "Мин.длина - 2, макс.длина - 80");
        }


    }
}
