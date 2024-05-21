package com.example.panache.resource;

import com.example.jpa.Customer;
import com.example.panache.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;


@Path("/api/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional(SUPPORTS)
public class CustomerResource {

  @Inject
  CustomerRepository repository;

  @GET
  @Path("{id}")
  public Customer findCustomerById(@PathParam("id") Long id) {
    return repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
  }

  @GET
  public List<Customer> listAllCustomers() {
    return repository.listAll();
  }

  @POST
  @Transactional
  public Response persistCustomer(Customer customer, @Context UriInfo uriInfo) {
    repository.persist(customer);
    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(customer.getId()));
    return Response.created(builder.build()).build();
  }

  @DELETE
  @Transactional
  @Path("/{id}")
  public void deleteCustomer(@PathParam("id") Long id) {
    repository.deleteById(id);
  }
}
