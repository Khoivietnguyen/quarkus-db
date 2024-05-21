package com.example.panache.resource;



import com.example.panache.model.Publisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;


@Path("/api/publishers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional(SUPPORTS)
public class PublisherResource {

  @GET
  @Path("/{id: \\d+}")
  public Publisher findPublisherById(@PathParam("id") Long id) {
    return (Publisher) Publisher.findByIdOptional(id).orElseThrow(NotFoundException::new);
  }

  @GET
  @Path("/{name: \\D+}")
  public Publisher findPublisherByName(@PathParam("name") String name) {
    return Publisher.findByName(name).orElseThrow(NotFoundException::new);
  }

  @GET
  @Path("/like/{name}")
  public List<Publisher> findPublisherContainingName(@PathParam("name") String name) {
    return Publisher.findContainingName(name);
  }

  @GET
  public List<Publisher> listAllPublishers() {
    return Publisher.listAll();
  }

  @POST
  @Transactional
  public Response persistPublisher(Publisher publisher, @Context UriInfo uriInfo) {
    Publisher.persist(publisher);
    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(publisher.id));
    return Response.created(builder.build()).build();
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  public void deletePublisher(@PathParam("id") Long id) {
    Publisher.deleteById(id);
  }
}
