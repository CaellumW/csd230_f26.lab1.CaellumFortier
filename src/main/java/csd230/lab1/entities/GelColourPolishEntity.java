package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GEL_COLOUR")
public class GelColourPolishEntity extends GelPolishEntity{
    @Column(name = "glitter")
    private boolean glitter;

    public GelColourPolishEntity() {
    }

    public GelColourPolishEntity(int stock, String shadeName, String gelType) {
        super(stock, shadeName, gelType);
    }

    public GelColourPolishEntity(boolean glitter) {
        this.glitter = glitter;
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

    @Override
    public String toString() {
        return "GelColourPolishEntity{" +
                "glitter=" + glitter +
                "} " + super.toString();
    }
}
