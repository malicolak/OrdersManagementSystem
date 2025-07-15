package view;

import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;

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
    private User user;
    public DashboardUI(User user) {
        this.user = user;
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
    }

}
