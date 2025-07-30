package dao;

import core.DBManagement;
import entity.Customer;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDao {
    private Connection connection;
    public CustomerDao(){
        this.connection = DBManagement.getInstance();
    }
    public ArrayList<Customer> findAll(){
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("SELECT * FROM customers");
            while(rs.next()){
                customers.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
    public ArrayList<Customer> queries(String query){
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while(rs.next()){
                customers.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    public boolean save(Customer customer){
        String sql = "INSERT INTO customers(name, type, phone, mail, address) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setObject(2, customer.getType().name(), Types.OTHER);
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getMail());
            ps.setString(5, customer.getAddress());
            return ps.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int id){
        String sql = "DELETE FROM customers WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Customer customer){
        String sql = "UPDATE customers SET name = ?, type = ?, phone = ?, mail = ?, address = ? WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setObject(2, customer.getType().name(), Types.OTHER);
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getMail());
            ps.setString(5, customer.getAddress());
            ps.setInt(6, customer.getId());
            return ps.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Customer getById(int id){
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                customer = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer match(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setPhone(rs.getString("phone"));
        customer.setMail(rs.getString("mail"));
        customer.setAddress(rs.getString("address"));
        customer.setType(Customer.CustomerType.valueOf(rs.getString("type")));
        return customer;
    }
}
