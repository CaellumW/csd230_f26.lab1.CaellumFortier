package csd230.lab1.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@DiscriminatorValue("MAGAZINE")
public class MagazineEntity extends PublicationEntity {
    @Column(name = "orderQty")
    private int orderQty;
    @Column(name = "currentIssue")
    private LocalDateTime currentIssue;

    public MagazineEntity() {
    }

    public MagazineEntity(String t, double p, int c, int o, LocalDateTime d) {
        super(t, p, c);
        this.orderQty = o;
        this.currentIssue = d;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int o) {
        this.orderQty = o;
    }

    public void setCurrentIssue(LocalDateTime d) {
        this.currentIssue = d;
    }

    public LocalDateTime getCurrentIssue() {
        return currentIssue;
    }

    @Override
    public String toString() {
        return "Mag{issue=" + currentIssue + ", " + super.toString() + "}";
    }
}
