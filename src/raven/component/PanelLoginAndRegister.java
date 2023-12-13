package raven.component;

import com.raven.swing.Button;
import raven.swing.MyPasswordField;
import raven.swing.MyTextField;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;



public class PanelLoginAndRegister extends javax.swing.JLayeredPane {
    MyTextField txtTenDangnhap =new MyTextField();
    MyTextField txtHoTen = new MyTextField();
    MyTextField txtEmail = new MyTextField();
    MyPasswordField txtPassDangnhap =new MyPasswordField();
    MyPasswordField txtPassDangky =new MyPasswordField();
    MyTextField Diachi = new MyTextField();
    MyTextField id = new MyTextField();
    Button btnDangnhap=new Button();
    Button btnDangky=new Button();
    JButton cmdForget = new JButton("Quên Mật Khẩu ?");
    MyTextField txtSoDienThoai =new MyTextField();
//    txtDiaChi Diachi=new txtDiaChi();
    public PanelLoginAndRegister() {
        initComponents();
        initRegister();
        initLogin();
        login.setVisible(false);
        register.setVisible(true);
    }

    private void initRegister() {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]10[]10[]10[]10[]25[]push"));
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        register.add(label);
        
        txtHoTen.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/user.png")));
        txtHoTen.setHint("Họ và tên");
        register.add(txtHoTen, "w 60%");
        
        txtEmail.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/mail.png")));
        txtEmail.setHint("Email");
        register.add(txtEmail, "w 60%");
              
        txtPassDangky.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/pass.png")));
        txtPassDangky.setHint("Password");        
        register.add(txtPassDangky, "w 60%");
        
        Diachi.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/home.png")));
        Diachi.setHint("Địa chỉ");        
        register.add(Diachi, "w 60%");
        
        id.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/person.png")));
        id.setHint("Mã nhân viên");        
        register.add(id, "w 60%");
        
        txtSoDienThoai.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/home.png")));
        txtSoDienThoai.setHint("Số điện thoại");        
        register.add(txtSoDienThoai, "w 60%");      
        
        btnDangky.setBackground(new Color(7, 164, 121));
        btnDangky.setForeground(new Color(250, 250, 250));
        btnDangky.setText("Đăng ký");
        register.add(btnDangky, "w 40%, h 40");
        
    }

    private void initLogin() {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Đăng Nhập");
        label.setFont(new Font("sansserif", 1, 30));
        label.setForeground(new Color(7, 164, 121));
        login.add(label);
//        MyTextField txtEmail = new MyTextField();//dddd
        txtTenDangnhap.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/mail.png")));
        txtTenDangnhap.setHint("Tên đăng nhập");
        login.add(txtTenDangnhap, "w 60%");
//        MyPasswordField txtPass = new MyPasswordField();
        txtPassDangnhap.setPrefixIcon(new ImageIcon(getClass().getResource("/raven/icon/pass.png")));
        txtPassDangnhap.setHint("Mật khẩu");
        login.add(txtPassDangnhap, "w 60%");
//        JButton cmdForget = new JButton("Forgot your password ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sansserif", 1, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget);
//        Button cmd = new Button();
        btnDangnhap.setBackground(new Color(7, 164, 121));
        btnDangnhap.setForeground(new Color(250, 250, 250));
        btnDangnhap.setText("ĐĂNG NHẬP");
        login.add(btnDangnhap, "w 40%, h 40");
    }

    public void showRegister(boolean show) {
        if (show) {
            register.setVisible(true);
            login.setVisible(false);
        } else {
            register.setVisible(false);
            login.setVisible(true);
        }
    }

    public JButton getCmdForget() {
        return cmdForget;
    }

    public MyTextField getTxtTenDangnhap() {
        return txtTenDangnhap;
    }

    public MyTextField getTxtHoTen() {
        return txtHoTen;
    }

    public MyTextField getTxtEmail() {
        return txtEmail;
    }

    public MyPasswordField getTxtPassDangnhap() {
        return txtPassDangnhap;
    }

    public MyPasswordField getTxtPassDangky() {
        return txtPassDangky;
    }

    public MyTextField getDiachi() {
        return Diachi;
    }

    public MyTextField getId() {
        return id;
    }

   

    public Button getBtnDangnhap() {
        return btnDangnhap;
    }

    public Button getBtnDangky() {
        return btnDangky;
    }

    public MyTextField getTxtSoDienThoai() {
        return txtSoDienThoai;
    }

    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
