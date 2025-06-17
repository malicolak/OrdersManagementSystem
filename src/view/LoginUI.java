package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_top;
    private JTextField fld_mail;
    private JPanel pnl_main;
    private JButton button_entry;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JPasswordField fl_password;

    public LoginUI() {
        this.add(container);
        this.setTitle("Sipariş Yönetim Sistemi");
        this.setSize(400,400);
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.button_entry.addActionListener(e -> {

        });
    }

}
