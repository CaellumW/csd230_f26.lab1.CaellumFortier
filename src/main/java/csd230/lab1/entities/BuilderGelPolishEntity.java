package csd230.lab1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BUILDER_GEL")
public class BuilderGelPolishEntity extends GelPolishEntity{
    @Column(name = "consistency")
    private String consistency;

    public BuilderGelPolishEntity() {
    }

    public BuilderGelPolishEntity(String consistency) {
        this.consistency = consistency;
    }

    public BuilderGelPolishEntity(int stock, String shadeName, String gelType, String consistency) {
        super(stock, shadeName, gelType);
        this.consistency = consistency;
    }
}
