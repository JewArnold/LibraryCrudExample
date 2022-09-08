package com.example.SpringBootLibrary.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Customer")
public class Customer {


    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int birthYear;


    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "customer")
    private List<Book> books;

}
