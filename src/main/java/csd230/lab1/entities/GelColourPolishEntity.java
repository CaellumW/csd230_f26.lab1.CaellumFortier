package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GEL_COLOUR")
public class GelColourPolishEntity extends GelPolishEntity{
    @Column(name = "glitter")
    private boolean glitter;
    @Column(name = "colour")
    private String colour;

    public GelColourPolishEntity() {
    }

    public GelColourPolishEntity(int stock, String shadeName, String gelType) {
        super(stock, shadeName, gelType);
    }

    public GelColourPolishEntity(boolean glitter, String colour) {
        this.glitter = glitter;
        this.colour = colour;
    }

    public GelColourPolishEntity(int stock, String shadeName, String gelType, boolean glitter) {
        super(stock, shadeName, gelType);
        this.glitter = glitter;
    }

    public boolean isGlitter() {
        return glitter;
    }

    public void setGlitter(boolean glitter) {
        this.glitter = glitter;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "GelColourPolishEntity{" +
                "glitter=" + glitter +
                "} " + super.toString();
    }
}
