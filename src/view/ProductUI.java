package view;

import business.ProductController;
import core.Helper;
import entity.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductUI extends JFrame {
    private JPanel container;
    private JTextField fld_name;
    private JTextField fld_code;
    private JTextField fld_price;
    private JTextField fld_stock;
    private JLabel lbl_name;
    private JLabel lbl_code;
    private JLabel lbl_price;
    private JLabel lbl_stock;
    private JButton btn_save;
    private JLabel lbl_title;
    private Product product;
    private ProductController productController;

    public ProductUI(Product product){
        this.product = product;
        this.productController = new ProductController();

        this.add(container);
        this.setTitle("Ürün Ekle ve Düzenle");
        this.setSize(500,350);
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        if(this.product.getId() == 0){
            this.lbl_title.setText("Ürün Ekle!");
        }else{
            this.lbl_title.setText("Ürün Düzenle!");
            this.fld_name.setText(this.product.getName());
            this.fld_code.setText(this.product.getCode());
            this.fld_price.setText(String.valueOf(this.product.getPrice()));
            this.fld_stock.setText(String.valueOf(this.product.getStock()));
        }
        btn_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_name,this.fld_code,this.fld_price,this.fld_stock};
            if(Helper.isFieldListEmpty(checkList)){
                Helper.showMsgPnl("fill");
            } else {
                try {
                    boolean isSave = false;
                    this.product.setName(this.fld_name.getText());
                    this.product.setCode(this.fld_code.getText());
                    this.product.setPrice(Integer.parseInt(this.fld_price.getText()));
                    this.product.setStock(Integer.parseInt(this.fld_stock.getText()));
                    if (Integer.parseInt(this.fld_price.getText()) <= 0 || Integer.parseInt(this.fld_stock.getText()) <= 0) {
                        Helper.showMsgPnl("Stok ve fiyat bilgisi negatif olamaz!");
                    } else {
                        if (this.product.getId() == 0) {
                            isSave = this.productController.save(this.product);

                        } else {
                            isSave = this.productController.update(this.product);
                        }
                        if (isSave) {
                            dispose();
                            Helper.showMsgPnl("info");
                        } else {
                            Helper.showMsgPnl("error");
                        }
                    }}catch(NumberFormatException ex){
                        Helper.showMsgPnl("Lütfen fiyat ve stok bilgisine sadece rakam giriniz!");
                    }
                }
        });
    }
}
