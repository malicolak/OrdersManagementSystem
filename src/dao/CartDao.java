package dao;

import business.CustomerController;
import business.ProductController;
import core.DBManagement;
import entity.Basket;
import entity.Cart;
import entity.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CartDao {
    private CustomerDao customerDao;
    private ProductDao productDao;
    private Connection connection;
    public CartDao(){
        this.connection = DBManagement.getInstance();
        this.customerDao = new CustomerDao();
        this.productDao = new ProductDao();
    }
    public ArrayList<Cart> findAll(){
        ArrayList<Cart> carts = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM carts");
            while(rs.next()){
                carts.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carts;
    }
    public boolean save(Cart cart){
        String query = "INSERT INTO carts (customer_id, product_id, price, date, note) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, cart.getCustomerId());
            ps.setInt(2, cart.getProductId());
            ps.setInt(3, cart.getPrice());
            ps.setDate(4, Date.valueOf(cart.getDate()));
            ps.setString(5, cart.getNote());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public Cart match(ResultSet rs) throws SQLException {
        Cart cart = new Cart();
        cart.setId(rs.getInt("id"));
        cart.setProductId(rs.getInt("product_id"));
        cart.setProduct(this.productDao.getById(cart.getProductId()));
        cart.setCustomerId(rs.getInt("customer_id"));
        cart.setCustomer(this.customerDao.getById(cart.getCustomerId()));
        cart.setPrice(rs.getInt("price"));
        cart.setDate(LocalDate.parse(rs.getString("date")));
        cart.setNote(rs.getString("note"));
        return cart;
    }
}
