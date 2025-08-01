package business;

import core.Helper;
import core.Item;
import dao.ProductDao;
import entity.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductDao productDao = new ProductDao();
    public ArrayList<Product> findAll(){
        return this.productDao.findAll();
    }
    public Product getById(int id){
        return this.productDao.getById(id);
    }
    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsgPnl(id + "ID numaralı ürün bulunamadı");
            return false;
        }
        return this.productDao.delete(id);
    }
    public boolean save(Product product){
        return this.productDao.save(product);
    }
    public boolean update(Product product){
        return this.productDao.update(product);
    }
    public ArrayList<Product> queries(String name, String code, Item isStock){
        String query = "SELECT * FROM products";
        ArrayList<String> whereList = new ArrayList<>();

        if(name.length() > 0){
            whereList.add("name ILIKE '%" + name + "%'");
        }
        if(code.length() > 0){
            whereList.add("code ILIKE '%" + code + "%'");
        }
        if(isStock != null){
            if(isStock.getKey() == 1) whereList.add("stock > 0");
            else whereList.add("stock <= 0");
        }

        if(whereList.size() > 0){
            String whereQuery = String.join(" AND ", whereList);
            query += " WHERE " + whereQuery;
        }
        return this.productDao.queries(query);
    }
}
