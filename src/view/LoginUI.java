package view;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private JPanel container;

    public LoginUI() {
        this.add(container);
        this.setTitle("Sipariş Yönetim Sistemi");
        this.setSize(400,400);
        this.setVisible(true);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

    }

}
