package csd230.lab1.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity @DiscriminatorValue("ORDER")
@Table(name = "order_entity")
public class OrderEntity extends PublicationEntity {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )

    private Set<ProductEntity> products = new LinkedHashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    private double totalAmount;
    private LocalDateTime orderDate;



    public OrderEntity() {
    }

    public OrderEntity(String t, double p, int c) {
        super(t, p, c);
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderEntity that = (OrderEntity) o;
        return id == that.id && Double.compare(totalAmount, that.totalAmount) == 0 && Objects.equals(products, that.products) && Objects.equals(orderDate, that.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), products, id, totalAmount, orderDate);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                "} " + super.toString();
    }
}
