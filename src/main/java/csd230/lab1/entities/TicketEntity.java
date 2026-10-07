package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TICKET")
public class TicketEntity extends ProductEntity {
    private String description;

    public TicketEntity() {
    }

    public TicketEntity(String description) {
        this.description = description;
    }

    @Override
    public void sellItem() {
        System.out.println("Selling Ticket: " + description + " for $" + super.getPrice());
    }

    @Override
    public double getPrice() {
        return super.getPrice();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        this.description = d;
    }

    public void setPrice(double p) {
        super.setPrice(p);
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "description='" + description + '\'' +
                "} " + super.toString();
    }
}
