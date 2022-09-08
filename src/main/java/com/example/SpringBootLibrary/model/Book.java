package com.example.SpringBootLibrary.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "Book")
public class Book {

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private int year;

    @Column(name = "author")
    private String author;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

}
