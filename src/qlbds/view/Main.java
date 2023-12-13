package qlbds.view;

import raven.component.PanelCover;
import raven.component.PanelLoginAndRegister;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import qlbds.DAO.NhanVienDao;
import qlbds.helper.DialogHelper;
import qlbds.helper.EmailSender;
import qlbds.helper.JdbcHelper;
import qlbds.helper.ShareHelper;
import qlbds.model.NhanVien;
import raven.component.PanelLoginAndRegister;
import raven.swing.MyPasswordField;
import raven.swing.MyTextField;


public class Main extends javax.swing.JFrame {

    NhanVienDao dao = new NhanVienDao();
    private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoginAndRegister loginAndRegister = new PanelLoginAndRegister();
    private boolean isLogin = true;
    private final double addSize = 30;
    private final double coverSize = 40;
    private final double loginSize = 60;

    private MyPasswordField PasswordDangnhap;
    private MyTextField Tendangnhap;

    private MyPasswordField PasswordDangky;
    private MyTextField Hoten;
    private MyTextField Email;
    private MyTextField Diachi;
    private MyTextField Manhanvien;
    private MyTextField Sodienthoai;

    private JButton ForgetPassword;

    void login() {
        String manv = Tendangnhap.getText();
        String matKhau = new String(PasswordDangnhap.getPassword());
        try {
            NhanVien nhanVien = dao.findByIdAndPass(manv,matKhau);
            if (nhanVien != null) {
                
                
                    ShareHelper.USER = nhanVien;
                    DialogHelper.alert(this, "Đăng nhập thành công!");
                    new MainJFrame().setVisible(true);
                    this.dispose();
                 
            } else {
                 DialogHelper.alert(this, "Tên đăng nhập hoặc mật khẩu không đúng");
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        NhanVien model = getModel();
        try {
            dao.insert(model);
            this.clear();
            DialogHelper.alert(this, "Đăng ký thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Đăng ký thất bại!");
        }

    }

    NhanVien getModel() {
        NhanVien model = new NhanVien();
        model.setMaNV(Manhanvien.getText());
        model.setHoTen(Hoten.getText());
        model.setMatKhau(new String(PasswordDangky.getPassword()));
        model.setVaiTro(false);
        model.setEmail(Email.getText());
        model.setDiaChi(Diachi.getText());
        model.setSoDt(Sodienthoai.getText());
        return model;
    }

    void clear() {
        Hoten.setText("");
        Email.setText("");
        Diachi.setText("");
        PasswordDangky.setText("");
        Manhanvien.setText("");
        Sodienthoai.setText("");

    }

    public Main() {
        initComponents();
        init();

        PasswordDangnhap = loginAndRegister.getTxtPassDangnhap();
        Tendangnhap = loginAndRegister.getTxtTenDangnhap();
        PasswordDangky = loginAndRegister.getTxtPassDangky();
        Email = loginAndRegister.getTxtEmail();
        Hoten = loginAndRegister.getTxtHoTen();
        Diachi = loginAndRegister.getDiachi();
        Manhanvien = loginAndRegister.getId();
        ForgetPassword = loginAndRegister.getCmdForget();
        Sodienthoai = loginAndRegister.getTxtSoDienThoai();
        loginAndRegister.getBtnDangnhap().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); // 
                login();
            }

        });

        loginAndRegister.getBtnDangky().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                if (checkRegister()) {
                    insert();
                    clear();
                }

            }

        });
        loginAndRegister.getCmdForget().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
                // Hiển thị hộp thoại JOptionPane để nhập email
                String email = JOptionPane.showInputDialog("Nhập email của bạn:");

                // Kiểm tra email tồn tại trong cơ sở dữ liệu 
                if (email != null && isEmailExist(email)) {
                    // Tạo mật khẩu ngẫu nhiên
                    String newPassword = generateRandomPassword(8);

                    // Cập nhật mật khẩu mới vào cơ sở dữ liệu
                    if (EmailSender.sendEmail(email, newPassword)) {
                        // Cập nhật mật khẩu mới trong cơ sở dữ liệu
                        updatePassword(email, newPassword);
                        JOptionPane.showMessageDialog(null, "Mật khẩu mới đã được gửi vào email của bạn.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi cập nhật mật khẩu.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Email không tồn tại.");
                }        // TODO add your handling code here:

            }

        });

    }

    private void init() {
        setTitle("ĐĂNG NHẬP");
        setIconImage(ShareHelper.getAppIcon());
        layout = new MigLayout("fill, insets 0");
        cover = new PanelCover();
        loginAndRegister = new PanelLoginAndRegister();
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = coverSize;
                if (fraction <= 0.5f) {
                    size += fraction * addSize;
                } else {
                    size += addSize - fraction * addSize;
                }
                if (isLogin) {
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    if (fraction >= 0.5f) {
                        cover.registerRight(fractionCover * 100);
                    } else {
                        cover.loginRight(fractionLogin * 100);
                    }
                } else {
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    if (fraction <= 0.5f) {
                        cover.registerLeft(fraction * 100);
                    } else {
                        cover.loginLeft((1f - fraction) * 100);
                    }
                }
                if (fraction >= 0.5f) {
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
                bg.revalidate();
            }

            @Override
            public void end() {
                isLogin = !isLogin;
            }
        };
        Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);  //  for smooth animation
        bg.setLayout(layout);
        bg.add(cover, "width " + coverSize + "%, pos " + (isLogin ? "1al" : "0al") + " 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos " + (isLogin ? "0al" : "1al") + " 0 n 100%"); //  1al as 100%
        loginAndRegister.showRegister(!isLogin);
        cover.login(isLogin);
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });
    }

    private boolean isEmailExist(String email) {
        // Trả về true nếu email tồn tại, ngược lại trả về false
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM nhanvien WHERE email = ?";
            resultSet = JdbcHelper.executeQuery(query, email);
            return resultSet.next(); // trả về true nếu email tồn tại
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Cập nhật mật khẩu mới vào cơ sở dữ liệu (thay thế bằng JDBC)
    private boolean updatePassword(String email, String newPassword) {
        try {
            String query = "UPDATE nhanvien SET matkhau = ? WHERE email = ?";
            JdbcHelper.executeUpdate(query, newPassword, email);
            return true; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức tạo mật khẩu ngẫu nhiên
    private String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }

        return password.toString();
    }

    public boolean checkRegister() {
        if (Hoten.getText().isBlank() || PasswordDangky.getText().isBlank() || Diachi.getText().isBlank() 
                ) {
            DialogHelper.alert(this, "Vui lòng  điền đủ thông tin");
            return false;
        }
        

        else if (!Email.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            DialogHelper.alert(this, "Email không đúng định dạng");
            return false;
        }

        else if (!Manhanvien.getText().isBlank() && !Manhanvien.getText().matches("^(?i)NV\\d+$")) {
            DialogHelper.alert(this, "Vui lòng nhập đúng định dạng mã: \n VD: NV123");
            return false;
        }
        else if (!Sodienthoai.getText().matches("^0\\d{9}$")) {
            DialogHelper.alert(this, "Số điện thoại không tồn tại");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 933, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables
}
