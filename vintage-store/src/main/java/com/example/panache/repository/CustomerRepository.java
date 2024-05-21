package com.example.panache.repository;

import com.example.jpa.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

  public List<Customer> listAllDans() {
    return list("firstName = 'Dan'", Sort.by("lastName"));
  }
}
