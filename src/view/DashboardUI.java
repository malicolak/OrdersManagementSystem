package view;

import business.CustomerController;
import core.Helper;
import entity.Customer;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DashboardUI extends JFrame {
    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JTextField fld_filtcust_name;
    private JComboBox cmb_filtcust_type;
    private JButton btn_filtcust_find;
    private JButton btn_filtcust_reset;
    private JButton btn_addcust;
    private JLabel lbl_filtcust_name;
    private JLabel lbl_filtcust_type;
    private JLabel lbl_top;
    private DefaultTableModel tmodel_customer;
    private User user;
    private CustomerController customerController;
    private JPopupMenu popupMenu_customer;
    public DashboardUI(User user) {
        this.tmodel_customer = new DefaultTableModel();
        this.user = user;
        this.customerController = new CustomerController();
        this.popupMenu_customer = new JPopupMenu();
        if(user == null){
            Helper.showMsgPnl("error");
            this.dispose();
        }
        this.add(container);
        this.setTitle("Sipariş Yönetim Sistemi");
        this.setSize(1000,800);
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.lbl_welcome.setText("Merhaba " + this.user.getName() + "!");
        this.btn_logout.addActionListener(e -> {
            dispose();
            LoginUI loginUI = new LoginUI();
        });

        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();

    }
    private void loadCustomerButtonEvent(){
        this.btn_addcust.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
        });
    }
    private void loadCustomerPopupMenu(){
        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               int selectedRow = tbl_customer.rowAtPoint(e.getPoint());
               tbl_customer.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });


        this.popupMenu_customer.add("Güncelle").addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
        });
        this.popupMenu_customer.add("Sil").addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
        });

        this.tbl_customer.setComponentPopupMenu(this.popupMenu_customer);
    }
    private void loadCustomerTable(ArrayList<Customer> customers){
        Object [] dataCustomer = {"ID", "Müşteri Adı", "Müşteri Tipi", "Telefon", "Mail", "Adres"};

        if(customers == null){
            customers = this.customerController.findAll();
        }

        //Tablo temizleme işlemi
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_customer.getModel();
        clearModel.setRowCount(0);

        this.tmodel_customer.setColumnIdentifiers(dataCustomer);
        for(Customer customer : customers){
            Object [] data = {
                    customer.getId(),
                    customer.getName(),
                    customer.getType().toString(),
                    customer.getPhone(),
                    customer.getMail(),
                    customer.getAddress()};
            this.tmodel_customer.addRow(data);
        }
        this.tbl_customer.setModel(this.tmodel_customer);
        this.tbl_customer.getTableHeader().setReorderingAllowed(false);
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_customer.setEnabled(false);


    }
}
