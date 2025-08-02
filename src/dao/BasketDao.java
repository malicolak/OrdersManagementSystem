package dao;

import core.DBManagement;
import entity.Basket;
import entity.Customer;
import entity.Product;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BasketDao {
    private Connection connection;
    private ProductDao productDao;

    public BasketDao(){
        this.connection = DBManagement.getInstance();
        this.productDao = new ProductDao();
    }
    public boolean save(Basket basket){
        String query = "INSERT INTO baskets (product_id) VALUES (?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, basket.getProductId());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public ArrayList<Basket> findAll(){
        ArrayList<Basket> baskets = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM baskets");
            while(rs.next()){
                baskets.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return baskets;
    }
    public Basket match(ResultSet rs) throws SQLException {
        Basket basket = new Basket();
        basket.setId(rs.getInt("id"));
        basket.setProductId(rs.getInt("product_id"));
        basket.setProduct(this.productDao.getById(basket.getProductId()));
        return basket;
    }
}
