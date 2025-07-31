package view;

import business.CustomerController;
import business.ProductController;
import core.Helper;
import entity.Customer;
import entity.Product;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
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
    private JComboBox<Customer.CustomerType> cmb_filtcust_type;
    private JButton btn_filtcust_find;
    private JButton btn_filtcust_reset;
    private JButton btn_addcust;
    private JLabel lbl_filtcust_name;
    private JLabel lbl_filtcust_type;
    private JPanel pnl_product;
    private JTable tbl_product;
    private JScrollPane scrl_product;
    private JPanel pnl_fltr_customer;
    private JPanel pnl_fltr_product;
    private JTextField fld_filtprod_name;
    private JTextField fld_filtprod_code;
    private JComboBox cbox_filtprod_stock;
    private JButton btn_filtprod_find;
    private JButton btn_filtprod_reset;
    private JButton btn_addprod;
    private JLabel lbl_filtprod_name;
    private JLabel lbl_filtprod_code;
    private JLabel lbl_filtprod_stock;
    private JLabel lbl_top;
    private DefaultTableModel tmodel_customer;
    private DefaultTableModel tmodel_product;
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private JPopupMenu popupMenu_customer;
    private JPopupMenu popupMenu_product;
    public DashboardUI(User user) {
        this.tmodel_customer = new DefaultTableModel();
        this.tmodel_product = new DefaultTableModel();
        this.user = user;
        this.customerController = new CustomerController();
        this.popupMenu_customer = new JPopupMenu();
        this.productController = new ProductController();
        this.popupMenu_product = new JPopupMenu();
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
        //Customer bölgesi
        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();
        this.cmb_filtcust_type.setModel(new DefaultComboBoxModel<>(Customer.CustomerType.values()));
        this.cmb_filtcust_type.setSelectedItem(null);

        //Product bölgesi
        loadProductTable(null);
        loadProductPopupMenu();
        loadProductButtonEvent();

    }
    private void loadProductButtonEvent(){
        this.btn_addprod.addActionListener(e -> {
            ProductUI productUI = new ProductUI(new Product());
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            }

            );
        });

    }
    private void loadProductPopupMenu(){
        this.tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });


        this.popupMenu_product.add("Güncelle").addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            ProductUI productUI = new ProductUI(this.productController.getById(selectedRowId));
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });
        this.popupMenu_product.add("Sil").addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            boolean isDelete = Helper.confirmMsgPnl("sure");
            if(isDelete){
                this.productController.delete(selectedRowId);
                loadProductTable(null);
                Helper.showMsgPnl("info");
            }
        });

        this.tbl_product.setComponentPopupMenu(this.popupMenu_product);
    }
    private void loadProductTable(ArrayList<Product> products){
        Object [] dataCustomer = {"ID", "Ürün Adı", "Ürün Kodu", "Fiyat", "Stok Durumu"};

        if(products == null){
            products = this.productController.findAll();
        }

        //Tablo temizleme işlemi
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        this.tmodel_product.setColumnIdentifiers(dataCustomer);
        for(Product product : products){
            Object [] data = {
                    product.getId(),
                    product.getName(),
                    product.getCode(),
                    product.getPrice(),
                    product.getStock()
                    };
            this.tmodel_product.addRow(data);
        }
        this.tbl_product.setModel(this.tmodel_product);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.setEnabled(false);

    }

    private void loadCustomerButtonEvent(){
        this.btn_addcust.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            }

            );
        });
        this.btn_filtcust_find.addActionListener(e -> {
            ArrayList<Customer> customers = this.customerController.queries(this.fld_filtcust_name.getText(), (Customer.CustomerType) this.cmb_filtcust_type.getSelectedItem());
            loadCustomerTable(customers);
        });
        this.btn_filtcust_reset.addActionListener(e -> {
            this.fld_filtcust_name.setText("");
            this.cmb_filtcust_type.setSelectedItem(null);
            loadCustomerTable(null);
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
            CustomerUI customerUI = new CustomerUI(this.customerController.getById(selectedRowId));
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });
        this.popupMenu_customer.add("Sil").addActionListener(e -> {
            int selectedRowId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            boolean isDelete =Helper.confirmMsgPnl("sure");
            if(isDelete){
                this.customerController.delete(selectedRowId);
                loadCustomerTable(null);
                Helper.showMsgPnl("info");
            }
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
