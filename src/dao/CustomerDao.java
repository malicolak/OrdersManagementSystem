package dao;

import core.DBManagement;
import entity.Customer;
import entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
