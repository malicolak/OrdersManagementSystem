package view;

import business.CustomerController;
import entity.Customer;

import javax.swing.*;
import java.awt.*;

public class CustomerUI extends JFrame {
    private JPanel container;
    private JLabel lbl_name;
    private JTextField fld_name;
    private JLabel lbl_type;
    private JComboBox<Customer.CustomerType> cbox_type;
    private JLabel lbl_phoneno;
    private JTextField fld_phoneno;
    private JLabel lbl_mail;
    private JTextField fld_mail;
    private JLabel lbl_address;
    private JButton button_save;
    private JTextArea tArea_address;
    private JLabel lbl_title;
    private Customer customer;
    private CustomerController customerController;


    public CustomerUI(Customer customer) {
        this.customer = customer;
        this.customerController = new CustomerController();
        this.add(container);
        this.setTitle("Müşteri Ekle ve Düzenle");
        this.setSize(500,800);
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.cbox_type.setModel(new DefaultComboBoxModel<>(Customer.CustomerType.values()));

        if(customer.getId() != 0){
            this.lbl_title.setText("Müşteri Ekle!");
        }else{
            this.lbl_title.setText("Müşteri Düzenle!");
        }
    }
}
