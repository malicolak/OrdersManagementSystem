package view;

import business.CustomerController;
import core.Helper;
import entity.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        if(this.customer.getId() == 0){
            this.lbl_title.setText("Müşteri Ekle!");
        }else{
            this.lbl_title.setText("Müşteri Düzenle!");
            this.fld_name.setText(this.customer.getName());
            this.cbox_type.getModel().setSelectedItem(this.customer.getType());
            this.fld_phoneno.setText(this.customer.getPhone());
            this.fld_mail.setText(this.customer.getMail());
            this.tArea_address.setText(this.customer.getAddress());
        }
        this.button_save.addActionListener(e -> {
            JTextField[] checkList = {this.fld_name,this.fld_phoneno};
            if(Helper.isFieldListEmpty(checkList)){
                Helper.showMsgPnl("fill");
            }else if(!Helper.isFieldEmpty(this.fld_mail) && !Helper.isValidEmail(this.fld_mail.getText())){
                Helper.showMsgPnl("Lütfen geçerli bir e-posta adresi giriniz!");
            }else{
                boolean isSave = false;
                this.customer.setName(this.fld_name.getText());
                this.customer.setType((Customer.CustomerType) this.cbox_type.getSelectedItem());
                this.customer.setPhone(this.fld_phoneno.getText());
                this.customer.setMail(this.fld_mail.getText());
                this.customer.setAddress(this.tArea_address.getText());
                if(this.customer.getId() == 0){
                    isSave = this.customerController.save(this.customer);
                }else {
                    isSave = this.customerController.update(this.customer);
                }

                if(isSave){
                    dispose();
                    Helper.showMsgPnl("info");

                }else{
                    Helper.showMsgPnl("error");
                }
            }
        });
    }
}
