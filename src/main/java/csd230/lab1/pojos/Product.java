package csd230.lab1.pojos;

import java.io.Serializable;

public abstract class Product extends Editable implements SaleableItem, Serializable {
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
