package entity;

import java.time.LocalDate;
import java.util.Date;

public class Cart {
    private int id;
    private int customerId;
    private Customer customer;
    private int productId;
    private Product product;
    private int price;
    private LocalDate date;
    private String note;

    public Cart(){

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public String toString(){
        return "Cart{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", customer=" + customer +
                ", productId=" + productId +
                ", product=" + product +
                ", price=" + price +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
