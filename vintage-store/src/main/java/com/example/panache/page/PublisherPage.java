package com.example.panache.page;

import com.example.panache.model.Publisher;
import io.quarkus.panache.common.Sort;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/page/publishers")
@Produces(MediaType.TEXT_HTML)
@ApplicationScoped
public class PublisherPage {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance publisher(Publisher publisher);

    public static native TemplateInstance publishers(List<Publisher> publishers);
  }

  @GET
  @Path("{id}")
  public TemplateInstance showPublisherById(@PathParam("id") Long id) {
    return Templates.publisher(Publisher.findById(id));
  }

  @GET
  public TemplateInstance showAllPublishers(@QueryParam("query") String query, @QueryParam("sort") @DefaultValue("id") String sort, @QueryParam("page") @DefaultValue("0") Integer pageIndex, @QueryParam("size") @DefaultValue("1000") Integer pageSize) {
    return Templates.publishers(Publisher.find(query, Sort.by(sort)).page(pageIndex, pageSize).list())
      .data("query", query)
      .data("sort", sort)
      .data("page", pageIndex)
      .data("size", pageSize);
  }
}
