package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PublicationEntity extends ProductEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "copies")
    private Integer copies;

    public PublicationEntity() {
    }

    public PublicationEntity(String title, Double price, Integer copies) {
        this.title = title;
        super.setPrice(price);
        this.copies = copies;
    }

    @Override
    public void sellItem() {
        if (copies > 0) {
            copies--;
            System.out.println("Sold '" + title + "'. Remaining copies: " + copies);
        } else {
            System.out.println("Cannot sell '" + title + "'. Out of stock.");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public Integer getCopies() {
        return copies;
    }

//    @Override
//    public double getPrice() {
//        return super.getPrice();
//    }
//    @Override
//    public void setPrice(double price) {
//        super.setPrice(price);
//    }

    public void setCopies(Integer c) {
        this.copies = c;
    }

    @Override
    public String toString() {
        return "PublicationEntity{" +
                "title='" + title + '\'' +
                ", copies=" + copies +
                "} " + super.toString();
    }
}
