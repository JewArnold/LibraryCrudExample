package com.example.SpringBootLibrary.services;



import com.example.SpringBootLibrary.model.Book;
import com.example.SpringBootLibrary.model.Customer;
import com.example.SpringBootLibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> findAll(String sort) {
        return bookRepository.findAll(Sort.by(sort));
    }

    public List<Book> findAll(int page, int booksPerPage) {
        PageRequest pageRequest = PageRequest.of(page, booksPerPage, Sort.by("year"));
        return bookRepository.findAll(pageRequest).getContent();
    }


    public Book findById(int id) {
        Book book = bookRepository.findById(id).orElse(null);

        return book;
    }

    public Optional<Book> findByName(String name) {
        return bookRepository.findBookByName(name);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void clearBook(int id) {
        Book book = findById(id);
        book.setCustomer(null);


    }

    @Transactional
    public void setCustomer(int id, Customer customer) {
        Book book = findById(id);
        book.setCustomer(customer);

    }

    public List<Book> findByNamePart(String name) {
        return bookRepository.findBookByNameLike(name);
    }


}
