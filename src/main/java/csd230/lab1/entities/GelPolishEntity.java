package csd230.lab1.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "GEL_POLISHES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class GelPolishEntity extends ProductEntity{
    @Column(name = "Stock")
    private int stock;
    @Column(name = "shade_name")
    private String shadeName;
    @Column(name = "gel_type")
    private String gelType;

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

    public GelPolishEntity(int stock, String shadeName, String gelType) {
        this.stock = stock;
        this.shadeName = shadeName;
        this.gelType = gelType;
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

    public String getGelType() {
        return gelType;
    }

    public void setGelType(String gelType) {
        this.gelType = gelType;
    }

    @Override
    public String toString() {
        return "GelPolishEntity{" +
                "stock=" + stock +
                ", shadeName='" + shadeName + '\'' +
                "} " + super.toString();
    }
}
