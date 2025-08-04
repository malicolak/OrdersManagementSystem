package view;

import business.BasketController;
import business.CartController;
import business.ProductController;
import core.Helper;
import entity.Basket;
import entity.Cart;
import entity.Customer;
import entity.Product;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CartUI extends JFrame{
    private JPanel container;
    private JLabel lbl_title;
    private JLabel lbl_customer_name;
    private JLabel lbl_cart_date;
    private JTextField fld_cart_date;
    private JTextArea tarea_cart_note;
    private JButton btn_cart_add;
    private JLabel lbl_cart_note;
    private Customer customer;
    private BasketController basketController;
    private CartController cartController;
    private ProductController productController;
    public CartUI(Customer customer){
        this.customer = customer;
        this.basketController = new BasketController();
        this.cartController = new CartController();
        this.productController = new ProductController();
        this.add(container);
        this.setTitle("Sipariş Ekranı!");
        this.setSize(500,350);
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        if(this.customer.getId() == 0){
            Helper.showMsgPnl("Lütfen geçerli bir müşteri seçiniz!");
            dispose();
        }

        ArrayList<Basket> baskets = this.basketController.findAll();
        if(baskets.size() == 0){
            Helper.showMsgPnl("Lütfen sepete ürün ekleyiniz!");
            dispose();
        }

        this.lbl_customer_name.setText("Müşteri Adı : " + this.customer.getName());

        btn_cart_add.addActionListener(e -> {
            try{
            if(Helper.isFieldEmpty(this.fld_cart_date)){
                Helper.showMsgPnl("Lütfen geçerli bir tarih giriniz!");
            }
            else{
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                ArrayList<Basket> processingBaskets = new ArrayList<>(baskets);
                for(Basket basket : processingBaskets){

                    if(basket.getProduct().getStock() <= 0) continue;

                    Cart cart = new Cart();
                    cart.setCustomerId(this.customer.getId());
                    cart.setProductId(basket.getProductId());
                    cart.setPrice(basket.getProduct().getPrice());
                    cart.setDate(LocalDate.parse(this.fld_cart_date.getText(),dtf));
                    cart.setNote(this.tarea_cart_note.getText());
                    this.cartController.save(cart);
                    basket.getProduct().setStock(basket.getProduct().getStock() - 1);
                    this.productController.update(basket.getProduct());

                    for(Basket tempBasket : processingBaskets){
                        if(tempBasket.getProductId() == basket.getProductId()){
                            tempBasket.getProduct().setStock(basket.getProduct().getStock());
                        }
                    }
                }
                Helper.showMsgPnl("info");
                this.basketController.clear();
                dispose();
            }}catch (Exception ex){
                ex.printStackTrace();
                Helper.showMsgPnl("Lütfen tarihi doğru giriniz!");
            }
        });
    }

    private void createUIComponents() throws ParseException {
        this.fld_cart_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.fld_cart_date.setText(dtf.format(LocalDate.now()));
    }
}
