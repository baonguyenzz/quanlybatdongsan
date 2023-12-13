/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package qlbds.view;

import SanPhamBDS.SanPham;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import qlbds.DAO.BatDongSanDAO;
import qlbds.DAO.DonHangDAO;
import qlbds.DAO.NguoiMuaDAO;
import qlbds.helper.DialogHelper;
import qlbds.helper.ShareHelper;
import qlbds.model.BatDongSan;
import qlbds.model.DonHang;
import qlbds.model.KhachHang;
import qlbds.model.NguoiMua;

/**
 *
 * @author baont
 */
public class QuanlydonhangJFrame extends javax.swing.JFrame {

    DonHangDAO dao = new DonHangDAO();
    List<Pair<DonHang, BatDongSan>> list;
    int index = -1;
    JFrame qldatmua;

    /**
     * Creates new form QuanlydonhangJFrame
     */
    public QuanlydonhangJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        LoadTable();
        setTitle("QUẢN LÝ ĐƠN HÀNG");
        setIconImage(ShareHelper.getAppIcon());
        clear();
    }

    public QuanlydonhangJFrame(BatDongSan bds, JFrame qldatmua) {
        initComponents();
        setLocationRelativeTo(null);
        LoadTable();
        setModel(bds);
        txtDonhang.setText(autoIncrease());
        this.qldatmua = qldatmua;
    }

    public String autoIncrease() {
        String lastid = dao.getLastId();
        System.out.println(lastid);
        if (lastid == null) {
            return "DH1";
        } else {
            String[] tachId = lastid.split("DH");
            int id = Integer.parseInt(tachId[1]) + 1;
            return "DH" + id;
        }
    }

    public void LoadTable() {
        Status(false);
        DefaultTableModel model = (DefaultTableModel) tblQLKH.getModel();
        model.setRowCount(0);
        try {
            list = new DonHangDAO().selectDetail();
            List<Object[]> listO = dao.ObjectDetailInfo(list);
            for (Object[] o : listO) {
                model.addRow(o);
            }
        } catch (SQLException e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            e.printStackTrace();
        }

    }

    void delete() {
        if (DialogHelper.confirm(this, "bạn có thực sự muốn xóa đơn hàng này không?")) {
            String madh = list.get(index).getKey().getMaDH();
            try {
                dao.delete(madh);
                this.LoadTable();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception a) {
                DialogHelper.alert(this, "Xóa Thất bại!");
            }
        }
    }

    void insert() {
        DonHang model = getModel();
        for (DonHang x : dao.select()) {
            if (model.getMaBds().equalsIgnoreCase(x.getMaBds())) {
                DialogHelper.alert(this, "Thêm mới thất bại!");
                break;
            } else {
                try {
                    dao.insert(model);
                    this.LoadTable();
                    this.clear();
                    DialogHelper.alert(this, "Thêm mới thành công!");
                    break;
                } catch (HeadlessException e) {
                    DialogHelper.alert(this, "Thêm mới thất bại!");
                    break;
                }
            }
        }
    }

    void Status(boolean x) {
        btnthem.setEnabled(!x);
        btnxoa.setEnabled(x);
    }

    void clear() {
        this.setModel(new BatDongSan(), new DonHang());
        txtDonhang.setText(autoIncrease());
                
        this.Status(false);
    }

    public void setModel(BatDongSan model, DonHang dh) {
        txtDonhang.setText(dh.getMaDH());
        txtName.setText(model.getTenBds());
        txtMaBDS.setText(model.getMaBds());
        txtDienTich.setText(String.valueOf(model.getDienTich()));
        txtGia.setText(String.valueOf(model.getGiaBds()));
        txtnguoitao.setText(ShareHelper.getUSER().getMaNV());
        txtCustomerName.setText(model.getMaKH());
        Date ngayhientai = new Date();
        txtNgayTao.setText(Utility.UtilityDate.DateToString(ngayhientai));
        txtDateTrade.setText(Utility.UtilityDate.DateToString(dh.getNgayGD()));
        txtBuyerName.setText(dh.getMaNM());
    }

    public void setModel(BatDongSan model) {
        txtName.setText(model.getTenBds());
        txtMaBDS.setText(model.getMaBds());
        txtDienTich.setText(String.valueOf(model.getDienTich()));
        txtGia.setText(String.valueOf(model.getGiaBds()));
        txtnguoitao.setText(ShareHelper.getUSER().getMaNV());
        txtCustomerName.setText(model.getMaKH());
        Date ngayhientai = new Date();
        txtNgayTao.setText(Utility.UtilityDate.DateToString(ngayhientai));
    }

    //lấy thông tin trên form của đơn hàng để tạo đơn hàng mới
    DonHang getModel() {
        DonHang model = new DonHang();
        model.setMaDH(txtDonhang.getText());
        model.setMaBds(txtMaBDS.getText());
        model.setMaNM(txtBuyerName.getText());
        model.setNgayGD(Utility.UtilityDate.StringToDate(txtDateTrade.getText()));
        return model;
    }

    public void arrow(String arrow) {
        switch (arrow) {
            case ">>":
                index++;
                if (index >= list.size()) {
                    index = list.size() - 1;
                }
                break;
            case ">|":
                index = list.size() - 1;
                break;
            case "<<":
                index--;
                if (index <= 0) {
                    index = 0;
                }
                break;
            case "|<":
                index = 0;
                break;
            default:
                throw new AssertionError();
        }
        Status(true);
        DonHang dh = list.get(index).getKey();
        BatDongSan bds = list.get(index).getValue();
        setModel(bds, dh);
    }

    private void exportToExcel(List<Pair<DonHang, BatDongSan>> listP, String filePath) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("QLDH");
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Mã đơn hàng");
            row.createCell(1).setCellValue("Ngày giao dịch");
            row.createCell(2).setCellValue("Mã bất động sản");
            row.createCell(3).setCellValue("Mã người mua");
            row.createCell(4).setCellValue("Mã khách hàng");
            row.createCell(5).setCellValue("Tên bất động sản");
            row.createCell(6).setCellValue("Diện tích bất động sản");
            row.createCell(7).setCellValue("Loại hình bất động sản");
            row.createCell(8).setCellValue("Giá trị hợp đồng");
            row.createCell(9).setCellValue("Hoa hồng");
            for (int rowIndex = 0; rowIndex < listP.size(); rowIndex++) {
                Row rowdata = sheet.createRow(rowIndex + 1);
                Pair<DonHang, BatDongSan> p = listP.get(rowIndex);

                rowdata.createCell(0).setCellValue(p.getKey().getMaDH());
                rowdata.createCell(1).setCellValue(p.getKey().getNgayGD());
                rowdata.createCell(2).setCellValue(p.getKey().getMaBds());
                rowdata.createCell(3).setCellValue(p.getKey().getMaNM());
                rowdata.createCell(4).setCellValue(p.getValue().getMaKH());
                rowdata.createCell(5).setCellValue(p.getValue().getTenBds());
                rowdata.createCell(6).setCellValue(p.getValue().getDienTich());
                rowdata.createCell(7).setCellValue(p.getValue().getLoaiBds());
                rowdata.createCell(8).setCellValue(p.getValue().getGiaBds());
                rowdata.createCell(9).setCellValue(p.getValue().getHoaHong());
            }

            // Đảm bảo đường dẫn đến thư mục tồn tại trong dự án
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();  // Tạo thư mục nếu chưa tồn tại
            }
            Date ngayhientai = new Date();
            // Tạo tên tệp dựa trên thời gian hiện tại để tránh trùng lặp
            String fileName = "QLDH_" + new SimpleDateFormat("dd-MM-yyyy").format(ngayhientai) + ".xls";
            String fullPath = filePath + File.separator + fileName;

            try (FileOutputStream fileOut = new FileOutputStream(fullPath)) {
                workbook.write(fileOut);
            }

            workbook.close();
            DialogHelper.alert(this, "Passing Excel. File saved at: " + fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // validation
    boolean checkInsert() {
        if (txtMaBDS.getText().isBlank() || txtName.getText().isBlank()
                || txtDateTrade.getText().isBlank() || txtCustomerName.getText().isBlank()
                || txtBuyerName.getText().isBlank() || txtnguoitao.getText().isBlank()
                || txtNgayTao.getText().isBlank()
                || txtGia.getText().isBlank() || txtDienTich.getText().isBlank()) {
            DialogHelper.alert(this, "Vui lòng điền đủ thông tin");
        } else if (!txtBuyerName.getText().isBlank() && !txtBuyerName.getText().matches("^(?i)NM\\d+$")) {
            DialogHelper.alert(this, "Vui lòng nhập đúng định dạng mã: \n VD: NM123");
            return false;
        } else if (!txtDienTich.getText().matches("^\\d+(\\.\\d+)?$")) {
            DialogHelper.alert(this, "Diện tích phải là số");
            return false;
        } else if (!txtGia.getText().matches("^\\d+(\\.\\d+)?$")) {
            DialogHelper.alert(this, "Giá phải là số");
            return false;
        }
        return true;
    }

    private boolean checkNM() {
        NguoiMuaDAO mn = new NguoiMuaDAO();
        List<NguoiMua> nm = mn.getAllNguoiMua();
        boolean found = false;
        for (int i = 0; i < nm.size(); i++) {
            if ((txtBuyerName.getText().equals(nm.get(i).getMaNM()))) {
                found = true;
            }
        }
        if (!found) {
            DialogHelper.alert(this, "Người mua không có trong danh sách");
        }
        return found;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser1 = new com.raven.datechooser.DateChooser();
        jLabel2 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBuyerName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtnguoitao = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnmoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtDateTrade = new javax.swing.JTextField();
        txtNgayTao = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDienTich = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtMaBDS = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        txtDonhang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblQLKH = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnTrangChu1 = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnXuatEx = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        dateChooser1.setTextRefernce(txtDateTrade);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 153));
        jLabel2.setText("QUẢN LÝ ĐƠN HÀNG");

        jLabel1.setText("Tên BĐS");

        jLabel3.setText("Ngày giao dịch");

        jLabel4.setText("Mã khách hàng");

        jLabel5.setText("Mã người mua");

        jLabel6.setText("Người tạo");

        txtnguoitao.setEditable(false);
        txtnguoitao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnguoitaoActionPerformed(evt);
            }
        });

        jLabel7.setText("Ngày tạo");

        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Add.png"))); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Delete.png"))); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnmoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Refresh.png"))); // NOI18N
        btnmoi.setText("Mới");
        btnmoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmoiActionPerformed(evt);
            }
        });

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/last.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/pri.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/first.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        txtDateTrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateTradeActionPerformed(evt);
            }
        });

        txtNgayTao.setEditable(false);
        txtNgayTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayTaoActionPerformed(evt);
            }
        });

        jLabel9.setText("Giá BĐS");

        txtGia.setEditable(false);

        jLabel10.setText("Diện tích");

        txtDienTich.setEditable(false);

        txtName.setEditable(false);

        jLabel8.setText("Mã BĐS");

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        txtDonhang.setBackground(new java.awt.Color(255, 204, 204));
        txtDonhang.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtDonhang.setFocusable(false);
        txtDonhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonhangActionPerformed(evt);
            }
        });

        jLabel11.setText("Mã đơn hàng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(309, 309, 309)
                                .addComponent(jLabel10))
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 199, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel1)
                                        .addGap(240, 240, 240))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtName)
                                        .addGap(22, 22, 22)))
                                .addGap(44, 44, 44)
                                .addComponent(jLabel3))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel4)
                                .addGap(252, 252, 252)
                                .addComponent(jLabel5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel6))
                                    .addComponent(txtnguoitao, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtBuyerName, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                            .addComponent(txtNgayTao)
                                            .addComponent(txtDateTrade)
                                            .addComponent(txtDienTich, javax.swing.GroupLayout.Alignment.TRAILING)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel8))
                                    .addComponent(txtMaBDS, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(58, 58, 58)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(txtDonhang, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnthem)
                        .addGap(24, 24, 24)
                        .addComponent(btnxoa)
                        .addGap(18, 18, 18)
                        .addComponent(btnmoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                        .addComponent(btnLast)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrev)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext)
                        .addGap(18, 18, 18)
                        .addComponent(btnFirst)
                        .addGap(31, 31, 31))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaBDS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDonhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDateTrade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuyerName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtnguoitao, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(txtNgayTao))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDienTich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnFirst, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnPrev, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnthem)
                                    .addComponent(btnxoa)
                                    .addComponent(btnmoi)))
                            .addComponent(btnLast)))
                    .addComponent(btnNext))
                .addGap(24, 24, 24))
        );

        tabs.addTab("CẬP NHẬP", jPanel2);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 739, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tblQLKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ ĐH", "NGÀY GD", "MÃ BĐS", "MÃ NM", "MÃ KH", "TÊN BĐS", "DIỆN TÍCH", "LOẠI HÌNH", "GIÁ TRỊ ĐH", "HOA HỒNG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQLKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQLKHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblQLKH);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("DANH SÁCH", jPanel3);

        jPanel5.setBackground(new java.awt.Color(232, 255, 255));

        btnTrangChu1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTrangChu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Home_1.png"))); // NOI18N
        btnTrangChu1.setText("Trang Chủ");
        btnTrangChu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChu1ActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Log out.png"))); // NOI18N
        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnXuatEx.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXuatEx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Text.png"))); // NOI18N
        btnXuatEx.setText("Xuất File Excel");
        btnXuatEx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTrangChu1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatEx, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(btnTrangChu1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnXuatEx, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(245, 245, 245))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnguoitaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnguoitaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnguoitaoActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        try {
            if (checkInsert() && checkNM()) {
                this.insert();
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi");
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnthemActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        delete();

    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmoiActionPerformed
        clear();
    }//GEN-LAST:event_btnmoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        arrow("|<");

    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        arrow("<<");
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        arrow(">>");
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        arrow(">|");
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtDateTradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateTradeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateTradeActionPerformed

    private void txtNgayTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayTaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTaoActionPerformed

    private void tblQLKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLKHMouseClicked
        index = tblQLKH.getSelectedRow();
        DonHang dh = list.get(index).getKey();
        BatDongSan bds = list.get(index).getValue();
        setModel(bds, dh);
        Status(true);
    }//GEN-LAST:event_tblQLKHMouseClicked

    private void btnTrangChu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChu1ActionPerformed
        this.dispose();
        if (qldatmua != null) {
            qldatmua.dispose();
        }
        new MainJFrame().setVisible(true);


    }//GEN-LAST:event_btnTrangChu1ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnXuatExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExActionPerformed
        List<Pair<DonHang, BatDongSan>> listP = dao.selectDetail(); // Lấy danh sách đơn hàng từ DAO
        String filePath = "C:\\Users\\baont\\Desktop\\File_Excel"; // Thay đổi đường dẫn tùy theo nơi bạn muốn lưu file
        exportToExcel(listP, filePath);//      
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void txtDonhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonhangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonhangActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(QuanlydonhangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanlydonhangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanlydonhangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanlydonhangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanlydonhangJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnTrangChu1;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.JButton btnmoi;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxoa;
    private com.raven.datechooser.DateChooser dateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblQLKH;
    private javax.swing.JTextField txtBuyerName;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtDateTrade;
    private javax.swing.JTextField txtDienTich;
    private javax.swing.JTextField txtDonhang;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaBDS;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtnguoitao;
    // End of variables declaration//GEN-END:variables
}
