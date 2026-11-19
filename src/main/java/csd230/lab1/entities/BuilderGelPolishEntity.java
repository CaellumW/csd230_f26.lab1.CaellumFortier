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

    public BuilderGelPolishEntity(Integer stock, String shadeName, String gelType, String consistency) {
        super(stock, shadeName, gelType);
        this.consistency = consistency;
    }

    public String getConsistency() {
        return consistency;
    }

    public void setConsistency(String consistency) {
        this.consistency = consistency;
    }

    @Override
    public String toString() {
        return "BuilderGelPolishEntity{" +
                "consistency='" + consistency + '\'' +
                "} " + super.toString();
    }
}
