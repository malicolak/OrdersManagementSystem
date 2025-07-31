package business;

import core.Helper;
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
}
