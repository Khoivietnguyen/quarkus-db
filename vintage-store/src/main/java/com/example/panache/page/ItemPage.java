package com.example.panache.page;

import com.example.panache.model.Book;
import com.example.panache.model.CD;
import io.quarkus.panache.common.Sort;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/page/items")
@Produces(MediaType.TEXT_HTML)
@ApplicationScoped
public class ItemPage {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance book(Book book);

    public static native TemplateInstance books(List<Book> books);

    public static native TemplateInstance cd(CD cd);

    public static native TemplateInstance cds(List<CD> cds);
  }

  @GET
  @Path("/books/{id}")
  public TemplateInstance showBookById(@PathParam("id") Long id) {
    return Templates.book(Book.findById(id));
  }

  @GET
  @Path("/books")
  public TemplateInstance showAllBooks(@QueryParam("query") String query, @QueryParam("sort") @DefaultValue("id") String sort, @QueryParam("page") @DefaultValue("0") Integer pageIndex, @QueryParam("size") @DefaultValue("1000") Integer pageSize) {
    return Templates.books(Book.find(query, Sort.by(sort)).page(pageIndex, pageSize).list())
      .data("query", query)
      .data("sort", sort)
      .data("pageIndex", pageIndex)
      .data("pageSize", pageSize);
  }

  @GET
  @Path("/cds/{id}")
  public TemplateInstance showCDById(@PathParam("id") Long id) {
    return Templates.cd(CD.findById(id));
  }

  @GET
  @Path("/cds")
  public TemplateInstance showAllCDs(@QueryParam("query") String query, @QueryParam("sort") @DefaultValue("id") String sort, @QueryParam("page") @DefaultValue("0") Integer pageIndex, @QueryParam("size") @DefaultValue("1000") Integer pageSize) {
    return Templates.cds(CD.find(query, Sort.by(sort)).page(pageIndex, pageSize).list())
      .data("query", query)
      .data("sort", sort)
      .data("pageIndex", pageIndex)
      .data("pageSize", pageSize);
  }
}
