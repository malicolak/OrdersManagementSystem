package dao;

import core.DBManagement;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private Connection connection;
    public UserDao() {
        this.connection = DBManagement.getInstance();
    }

    public User checkLogin(String mail, String password){
        User user = new User();
        String sql = "select * from user where mail = ? and password = ?";

        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, mail);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = this.match(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public ArrayList<User> findAll(){
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery("select * from user");
            while(rs.next()){
                users.add(this.match(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public User match(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setMail(rs.getString("mail"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
