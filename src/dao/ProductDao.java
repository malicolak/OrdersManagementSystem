package dao;

import core.DBManagement;
import entity.Customer;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDao {
    private Connection connection;
    public ProductDao(){
        this.connection = DBManagement.getInstance();
    }
    public ArrayList<Product> findAll(){
        ArrayList<Product> products = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM products");
            while(rs.next()){
                products.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    public Product getById(int id){
        Product product = null;
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                product = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public boolean delete(int id){
        String sql = "DELETE FROM products WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean save(Product product){
        String query = "INSERT INTO products (name, code, price, stock) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCode());
            ps.setInt(3, product.getPrice());
            ps.setInt(4, product.getStock());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Product product){
        String query = "UPDATE products SET name = ?, code = ?, price = ?, stock = ? WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getCode());
            ps.setInt(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setInt(5, product.getId());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Product match(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setCode(rs.getString("code"));
        product.setPrice(rs.getInt("price"));
        product.setStock(rs.getInt("stock"));
        return product;
    }
}
