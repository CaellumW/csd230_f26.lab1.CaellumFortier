package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Gel_polishes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GelPolishEntity extends ProductEntity{
    @Column(name = "Stock")
    private int stock;
    @Column(name = "shade_name")
    private String shadeName;


    @Override
    public void sellItem() {
        if (stock > 0) {
            stock--;
            System.out.println("Sold '" + shadeName + "'. Remaining stock: " + stock);
        } else {
            System.out.println("Cannot sell '" + shadeName + "'. Out of stock.");
        }
    }

    public GelPolishEntity() {
    }

    public GelPolishEntity(int stock, double price, String shadeName) {
        this.stock = stock;
        super.setPrice(price);
        this.shadeName = shadeName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getShadeName() {
        return shadeName;
    }

    public void setShadeName(String shadeName) {
        this.shadeName = shadeName;
    }

    @Override
    public String toString() {
        return "GelPolishEntity{" +
                "stock=" + stock +
                ", shadeName='" + shadeName + '\'' +
                "} " + super.toString();
    }
}
