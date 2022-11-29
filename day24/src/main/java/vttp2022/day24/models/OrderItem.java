package vttp2022.day24.models;

public class OrderItem {
    private Integer itemId;
    private String description;
    private Integer quantity;

    public OrderItem() {}
    public OrderItem(String description, Integer quantity) {
        this.description = description;
        this.quantity = quantity;
    }

    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
