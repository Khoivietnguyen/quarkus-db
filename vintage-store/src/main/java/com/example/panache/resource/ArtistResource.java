package com.example.panache.resource;

import com.exampe.jdbc.Artist;
import com.example.panache.repository.ArtistRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;


@Path("/api/artists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional(SUPPORTS)
public class ArtistResource {

  @Inject
  ArtistRepository repository;

  /**
   * curl http://localhost:8080/api/artists/1
   */
  @GET
  @Path("{id}")
  public Artist findArtistById(@PathParam("id") Long id) {
    return repository.findByIdOptional(id).orElseThrow(NotFoundException::new);
  }

  /**
   * curl http://localhost:8080/api/artists
   */
  @GET
  public List<Artist> listAllArtists() {
    return repository.listAllArtistsSorted();
  }

  /**
   * curl -X POST http://localhost:8080/api/artists -H 'Content-Type: application/json' -d '{ "bio": "artist bi", "name": "artist name" }' -v
   */
  @POST
  @Transactional
  public Response persistArtist(Artist artist, @Context UriInfo uriInfo) {
    repository.persist(artist);
    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(artist.getId()));
    return Response.created(builder.build()).build();
  }

  /**
   * curl -X DELETE http://localhost:8080/api/artists/1
   */
  @DELETE
  @Transactional
  @Path("/{id}")
  public void deleteArtist(@PathParam("id") Long id) {
    repository.deleteById(id);
  }

}
