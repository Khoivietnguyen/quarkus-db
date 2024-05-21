package com.example.panache.page;

import com.exampe.jdbc.Artist;
import com.example.panache.repository.ArtistRepository;
import io.quarkus.panache.common.Sort;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/page/artists")
@Produces(MediaType.TEXT_HTML)
@ApplicationScoped
public class ArtistPage {

  @Inject
  ArtistRepository repository;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance artist(Artist artist);

    public static native TemplateInstance artists(List<Artist> artists);
  }

  @GET
  @Path("{id}")
  public TemplateInstance showArtistById(@PathParam("id") Long id) {
    return Templates.artist(repository.findById(id));
  }

  @GET
  public TemplateInstance showAllArtists(@QueryParam("query") String query, @QueryParam("sort") @DefaultValue("id") String sort, @QueryParam("page") @DefaultValue("0") Integer pageIndex, @QueryParam("size") @DefaultValue("1000") Integer pageSize) {
    return Templates.artists(repository.find(query, Sort.by(sort)).page(pageIndex, pageSize).list())
      .data("query", query)
      .data("sort", sort)
      .data("pageIndex", pageIndex)
      .data("pageSize", pageSize);
  }
}
