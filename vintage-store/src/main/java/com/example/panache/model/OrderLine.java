package com.example.panache.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.Instant;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
@Entity
@Table(name = "t_purchase_order_lines")
public class OrderLine extends PanacheEntity {

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "item_fk")
  public Item item;

  @Column(nullable = false)
  public Integer quantity;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "purchase_order_fk")
  public PurchaseOrder purchaseOrder;

  @Column(name = "created_date", nullable = false)
  public Instant createdDate = Instant.now();

}
