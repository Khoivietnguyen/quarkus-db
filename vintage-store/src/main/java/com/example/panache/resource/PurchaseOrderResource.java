package com.example.panache.resource;



import com.example.panache.model.PurchaseOrder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;


@Path("/api/purchase-orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Transactional(SUPPORTS)
public class PurchaseOrderResource {

  @GET
  @Path("{id}")
  public PurchaseOrder findPurchaseOrderById(@PathParam("id") Long id) {
    return (PurchaseOrder) PurchaseOrder.findByIdOptional(id).orElseThrow(NotFoundException::new);
  }

  @GET
  public List<PurchaseOrder> listAllPurchaseOrders() {
    return PurchaseOrder.listAll();
  }

  @POST
  @Transactional
  public Response persistPurchaseOrder(PurchaseOrder purchaseOrder, @Context UriInfo uriInfo) {
    PurchaseOrder.persist(purchaseOrder);
    UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(purchaseOrder.id));
    return Response.created(builder.build()).build();
  }

  @DELETE
  @Transactional
  @Path("/{id}")
  public void deletePurchaseOrder(@PathParam("id") Long id) {
    PurchaseOrder.deleteById(id);
  }
}
