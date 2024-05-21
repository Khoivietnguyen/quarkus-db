package com.example.panache.page;

import com.example.panache.model.PurchaseOrder;
import io.quarkus.panache.common.Sort;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/page/purchase-orders")
@Produces(MediaType.TEXT_HTML)
@ApplicationScoped
public class PurchaseOrderPage {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance purchaseOrder(PurchaseOrder purchaseOrder);

    public static native TemplateInstance purchaseOrders(List<PurchaseOrder> purchaseOrders);
  }

  @GET
  @Path("{id}")
  public TemplateInstance showPurchaseOrderById(@PathParam("id") Long id) {
    return Templates.purchaseOrder(PurchaseOrder.findById(id));
  }

  @GET
  public TemplateInstance showAllPurchaseOrders(@QueryParam("query") String query, @QueryParam("sort") @DefaultValue("id") String sort, @QueryParam("page") @DefaultValue("0") Integer pageIndex, @QueryParam("size") @DefaultValue("1000") Integer pageSize) {
    return Templates.purchaseOrders(PurchaseOrder.find(query, Sort.by(sort)).page(pageIndex, pageSize).list())
      .data("query", query)
      .data("sort", sort)
      .data("pageIndex", pageIndex)
      .data("pageSize", pageSize);
  }
}
