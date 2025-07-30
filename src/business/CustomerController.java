package business;

import core.Helper;
import dao.CustomerDao;
import entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDao customerDao = new CustomerDao();
    public ArrayList<Customer> findAll(){
        return this.customerDao.findAll();
    }
    public boolean save(Customer customer){
        return this.customerDao.save(customer);
    }

    public Customer getById(int id){
        return this.customerDao.getById(id);
    }
    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsgPnl(id + "ID numaralı müşteri bulunamadı");
            return false;
        }
        return this.customerDao.delete(id);
    }
    public boolean update(Customer customer){
        if(this.getById(customer.getId()) == null) {
            Helper.showMsgPnl(customer.getId() + "ID numaralı müşteri bulunamadı");
            return false;
        }
            return this.customerDao.update(customer);
    }
    public ArrayList<Customer> queries(String name, Customer.CustomerType type){
        String query = "SELECT * FROM customers";
        ArrayList<String> whereList = new ArrayList<>();

        if(name.length() > 0){
            whereList.add("name ILIKE '%" + name + "%'");
        }
        if(type != null){
            whereList.add("type = '" + type.name() + "'");
        }
        if(whereList.size() > 0){
            String whereQuery = String.join(" AND ", whereList);
            query += " WHERE " + whereQuery;
        }
        return this.customerDao.queries(query);
    }
}
