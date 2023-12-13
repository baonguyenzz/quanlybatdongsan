/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package qlbds.view;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import qlbds.DAO.BatDongSanDAO;
import qlbds.DAO.NguoiMuaDAO;
import qlbds.helper.DialogHelper;
import qlbds.helper.ShareHelper;
import qlbds.model.BatDongSan;
import qlbds.model.NguoiMua;

/**
 *
 * @author Duy Linh
 */
public class QuanlynguoimuaJFrame extends javax.swing.JFrame {

    private NguoiMuaDAO nguoiMuaDAO;
    private DefaultTableModel tableModel;
    private int currentIndex = 0;
    private boolean isDarkMode = false;

    /**
     * Creates new form QuanlykhachhangJFrame
     */
    public QuanlynguoimuaJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ NGƯỜI MUA");
        setIconImage(ShareHelper.getAppIcon());
        nguoiMuaDAO = new NguoiMuaDAO();
        loadDataToTable();
        selectFirstRow();

        rowSorter = new TableRowSorter<>((DefaultTableModel) tblQlnm.getModel());
        tblQlnm.setRowSorter(rowSorter);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });

    }

    // Load dữ liệu từ cơ sở dữ liệu vào bảng
    private void loadDataToTable() {
        List<NguoiMua> nguoiMuaList = nguoiMuaDAO.getAllNguoiMua();
        tableModel = (DefaultTableModel) tblQlnm.getModel();
        tableModel.setRowCount(0);

        for (NguoiMua nguoiMua : nguoiMuaList) {
            tableModel.addRow(new Object[]{nguoiMua.getMaNM(), nguoiMua.getDiaChi(), nguoiMua.getTenNM(), nguoiMua.getSoDt(), nguoiMua.getMaNV()});
        }
    }

    private void selectFirstRow() {
        if (tblQlnm.getRowCount() > 0) {
            tblQlnm.setRowSelectionInterval(0, 0); // Select the first row
            tblQlnmMouseClicked(null); // Simulate a mouse click on the selected row
        }
    }

    private void selectRow() {
        int selectedRow = tblQlnm.getSelectedRow();
        if (selectedRow != -1) { // Check if a row is selected
            // Get data from the selected row
            String manm = tblQlnm.getValueAt(selectedRow, 0).toString();
            String diachi = tblQlnm.getValueAt(selectedRow, 1).toString();
            String tennm = tblQlnm.getValueAt(selectedRow, 2).toString();
            String sodt = tblQlnm.getValueAt(selectedRow, 3).toString();
            String manv = tblQlnm.getValueAt(selectedRow, 4).toString();
            txaSohuu.setText(null);
            List<BatDongSan> listbds = new BatDongSanDAO().SelectBdsNguoimua(manm);
            for (BatDongSan batDongSan : listbds) {
                fillSohuu(batDongSan);
            }
            // Set data to the text fields
            txtManm.setText(manm);
            txtDiachi.setText(diachi);
            txtTennm.setText(tennm);
            txtSdt.setText(sodt);
            txtManv.setText(manv);
            tabs.setSelectedIndex(0);
        }
    }

    private void showDataAtIndex(int index) {
        if (index >= 0 && index < tblQlnm.getRowCount()) {
            tblQlnm.setRowSelectionInterval(index, index);
            tblQlnm.scrollRectToVisible(tblQlnm.getCellRect(index, 0, true));
            tblQlnmMouseClicked(null); // Simulate a mouse click on the selected row
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Add your phone number validation logic here
        // For example, you may check if the phone number contains only digits and has a valid length
        return phoneNumber.matches("\\d{10}");
    }

    private boolean isValidFormData(String manm, String diachi, String tennm, String sodt) {
        // Validate the form data
        if (manm.isEmpty() || diachi.isEmpty() || tennm.isEmpty() || sodt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!isValidPhoneNumber(sodt)) {
            JOptionPane.showMessageDialog(this, "Invalid phone number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // You can add more specific validation logic as needed
        return true;
    }

    public void fillSohuu(BatDongSan bds) {
        txaSohuu.append("\n"
                + "Mã bất động sản : " + bds.getMaBds() + "\n"
                + "Tên bất động sản : " + bds.getTenBds() + "\n"
                + "Loại hình bất động sản : " + bds.getLoaiBds() + "\n"
                + "Diện tích : " + bds.getDienTich() + "\n"
        );
    }

    private void clearFields() {
        // New button click logic here
        txaSohuu.setText(null);
        txtManm.setText("");
        txtDiachi.setText("");
        txtTennm.setText("");
        txtSdt.setText("");
        txtManv.setText("");
        tblQlnm.clearSelection();
    }

    private int findRowIndexByMaNM(String maNM) {
        for (int i = 0; i < tblQlnm.getRowCount(); i++) {
            if (maNM.equals(tblQlnm.getValueAt(i, 0).toString())) {
                return i;
            }
        }
        return -1; // Trả về -1 nếu không tìm thấy
    }

    private void searchNguoiMua() {
        String keyword = txtSearch.getText();
        List<NguoiMua> searchResult = nguoiMuaDAO.searchNguoiMua(keyword);
        displaySearchResult(searchResult);
    }

    private void displaySearchResult(List<NguoiMua> searchResult) {
        DefaultTableModel model = (DefaultTableModel) tblQlnm.getModel();
        model.setRowCount(0);

        for (NguoiMua nguoiMua : searchResult) {
            model.addRow(new Object[]{nguoiMua.getMaNM(), nguoiMua.getDiaChi(), nguoiMua.getTenNM(), nguoiMua.getSoDt(), nguoiMua.getMaNV()});
        }
    }

//    private void exportToExcel(List<NguoiMua> nguoiMuaList, String filePath) {
//        try {
//            //tạo một đối tượng của lớp HSSFWorkbook
//            HSSFWorkbook workbook = new HSSFWorkbook();
//            //gọi phương thức creatSheet() và truyền tên file muốn tạo
//            HSSFSheet sheet = workbook.createSheet("QLNM");
//            //tạo hàng thứ 0 sử dụng phương thức createRow()
//            for (int rowIndex = 0; rowIndex < nguoiMuaList.size(); rowIndex++) {
//                Row row = sheet.createRow(rowIndex + 1);
//                NguoiMua nguoiMua = nguoiMuaList.get(rowIndex);
//            HSSFRow rowhead = sheet.createRow((short) 0);
//            //tạo ô bằng cách sử dụng phương thức createCell() và thiết lập giá trị cho ô bằng cách sử dụng phương thức setCellValue()
//            rowhead.createCell(0).setCellValue(nguoiMua.getMaNM());
//            rowhead.createCell(1).setCellValue(nguoiMua.getTenNM());
//            rowhead.createCell(2).setCellValue(nguoiMua.getSoDt());
//            rowhead.createCell(3).setCellValue(nguoiMua.getDiaChi());
//            rowhead.createCell(4).setCellValue(nguoiMua.getMaNV());
//            }
//            //đóng workbook
//            workbook.close();
//            //in thông báo tạo thành công
//            System.out.println("Passing Excel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private void updateTheme() {
        Color backgroundColor;
        Color buttonTextColor;

        if (isDarkMode) {
            // Dark mode settings
            backgroundColor = Color.WHITE;
            buttonTextColor = Color.BLACK;
        } else {
            // Light mode settings
            backgroundColor = Color.BLACK;
            buttonTextColor = Color.WHITE;
        }

        // Cập nhật màu sắc của các thành phần
        getContentPane().setBackground(backgroundColor);
        btnDark.setBackground(backgroundColor);
        btnDark.setForeground(buttonTextColor);
        btnOffDark.setBackground(backgroundColor);
        btnOffDark.setForeground(buttonTextColor);

        // Màu của các thành phần khác
        // JPanel QLNM
        pnlQlnm.setBackground(backgroundColor);
        pnlQlnm.setForeground(buttonTextColor);
        btnTrangChu1.setBackground(backgroundColor);
        btnTrangChu1.setForeground(buttonTextColor);
        btnXuatEx.setBackground(backgroundColor);
        btnXuatEx.setForeground(buttonTextColor);
        btnExit.setBackground(backgroundColor);
        btnExit.setForeground(buttonTextColor);

        // JPanelUpdate
        pnlUpdate.setBackground(backgroundColor);
        pnlUpdate.setForeground(buttonTextColor);

        lblManm.setBackground(backgroundColor);
        lblManm.setForeground(buttonTextColor);
        txtManm.setBackground(backgroundColor);
        txtManm.setForeground(buttonTextColor);
        lblDiachi.setBackground(backgroundColor);
        lblDiachi.setForeground(buttonTextColor);
        txtDiachi.setBackground(backgroundColor);
        txtDiachi.setForeground(buttonTextColor);
        lblTennm.setBackground(backgroundColor);
        lblTennm.setForeground(buttonTextColor);
        txtTennm.setBackground(backgroundColor);
        txtTennm.setForeground(buttonTextColor);
        lblSdt.setBackground(backgroundColor);
        lblSdt.setForeground(buttonTextColor);
        txtSdt.setBackground(backgroundColor);
        txtSdt.setForeground(buttonTextColor);
        lblManv.setBackground(backgroundColor);
        lblManv.setForeground(buttonTextColor);
        txtManv.setBackground(backgroundColor);
        txtManv.setForeground(buttonTextColor);

        btnAdd.setBackground(backgroundColor);
        btnAdd.setForeground(buttonTextColor);
        btnDelete.setBackground(backgroundColor);
        btnDelete.setForeground(buttonTextColor);
        btnUpdate.setBackground(backgroundColor);
        btnUpdate.setForeground(buttonTextColor);
        btnNew.setBackground(backgroundColor);
        btnNew.setForeground(buttonTextColor);
        btnFirst.setBackground(backgroundColor);
        btnFirst.setForeground(buttonTextColor);
        btnPrev.setBackground(backgroundColor);
        btnPrev.setForeground(buttonTextColor);
        btnnext.setBackground(backgroundColor);
        btnnext.setForeground(buttonTextColor);
        btnLast.setBackground(backgroundColor);
        btnLast.setForeground(buttonTextColor);

        // JPanel List
        pnlList.setBackground(backgroundColor);
        pnlList.setForeground(buttonTextColor);
        pnlSearch.setBackground(backgroundColor);
        pnlSearch.setForeground(buttonTextColor);
        pnlIconSearch.setBackground(backgroundColor);
        pnlIconSearch.setForeground(buttonTextColor);
        txtSearch.setBackground(backgroundColor);
        txtSearch.setForeground(buttonTextColor);
        tblQlnm.setBackground(backgroundColor);
        tblQlnm.setForeground(buttonTextColor);
        btnSxMa.setBackground(backgroundColor);
        btnSxMa.setForeground(buttonTextColor);
        btnSxName.setBackground(backgroundColor);
        btnSxName.setForeground(buttonTextColor);
        repaint();
    }

    private void offupdateTheme() {
        Color backgroundColor;
        Color buttonTextColor;

        if (isDarkMode) {
            // Dark mode settings
            backgroundColor = Color.BLACK;
            buttonTextColor = Color.WHITE;
        } else {
            // Light mode settings
            backgroundColor = Color.WHITE;
            buttonTextColor = Color.BLACK;
        }

        // Cập nhật màu sắc của các thành phần
        getContentPane().setBackground(backgroundColor);
        btnDark.setBackground(backgroundColor);
        btnDark.setForeground(buttonTextColor);
        btnOffDark.setBackground(backgroundColor);
        btnOffDark.setForeground(buttonTextColor);

        // Màu của các thành phần khác
        // JPanel QLNM
        pnlQlnm.setBackground(backgroundColor);
        pnlQlnm.setForeground(buttonTextColor);
        btnTrangChu1.setBackground(backgroundColor);
        btnTrangChu1.setForeground(buttonTextColor);
        btnXuatEx.setBackground(backgroundColor);
        btnXuatEx.setForeground(buttonTextColor);
        btnExit.setBackground(backgroundColor);
        btnExit.setForeground(buttonTextColor);

        // JPanelUpdate
        pnlUpdate.setBackground(backgroundColor);
        pnlUpdate.setForeground(buttonTextColor);

        lblManm.setBackground(backgroundColor);
        lblManm.setForeground(buttonTextColor);
        txtManm.setBackground(backgroundColor);
        txtManm.setForeground(buttonTextColor);
        lblDiachi.setBackground(backgroundColor);
        lblDiachi.setForeground(buttonTextColor);
        txtDiachi.setBackground(backgroundColor);
        txtDiachi.setForeground(buttonTextColor);
        lblTennm.setBackground(backgroundColor);
        lblTennm.setForeground(buttonTextColor);
        txtTennm.setBackground(backgroundColor);
        txtTennm.setForeground(buttonTextColor);
        lblSdt.setBackground(backgroundColor);
        lblSdt.setForeground(buttonTextColor);
        txtSdt.setBackground(backgroundColor);
        txtSdt.setForeground(buttonTextColor);
        lblManv.setBackground(backgroundColor);
        lblManv.setForeground(buttonTextColor);
        txtManv.setBackground(backgroundColor);
        txtManv.setForeground(buttonTextColor);

        btnAdd.setBackground(backgroundColor);
        btnAdd.setForeground(buttonTextColor);
        btnDelete.setBackground(backgroundColor);
        btnDelete.setForeground(buttonTextColor);
        btnUpdate.setBackground(backgroundColor);
        btnUpdate.setForeground(buttonTextColor);
        btnNew.setBackground(backgroundColor);
        btnNew.setForeground(buttonTextColor);
        btnFirst.setBackground(backgroundColor);
        btnFirst.setForeground(buttonTextColor);
        btnPrev.setBackground(backgroundColor);
        btnPrev.setForeground(buttonTextColor);
        btnnext.setBackground(backgroundColor);
        btnnext.setForeground(buttonTextColor);
        btnLast.setBackground(backgroundColor);
        btnLast.setForeground(buttonTextColor);

        // JPanel List
        pnlList.setBackground(backgroundColor);
        pnlList.setForeground(buttonTextColor);
        pnlSearch.setBackground(backgroundColor);
        pnlSearch.setForeground(buttonTextColor);
        pnlIconSearch.setBackground(backgroundColor);
        pnlIconSearch.setForeground(buttonTextColor);
        txtSearch.setBackground(backgroundColor);
        txtSearch.setForeground(buttonTextColor);
        tblQlnm.setBackground(backgroundColor);
        tblQlnm.setForeground(buttonTextColor);
        btnSxMa.setBackground(backgroundColor);
        btnSxMa.setForeground(buttonTextColor);
        btnSxName.setBackground(backgroundColor);
        btnSxName.setForeground(buttonTextColor);
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        pnlUpdate = new javax.swing.JPanel();
        lblManm = new javax.swing.JLabel();
        txtManm = new javax.swing.JTextField();
        lblDiachi = new javax.swing.JLabel();
        lblTennm = new javax.swing.JLabel();
        lblSdt = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnnext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtTennm = new javax.swing.JTextField();
        txtDiachi = new javax.swing.JTextField();
        lblManv = new javax.swing.JLabel();
        txtManv = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaSohuu = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblQlnm = new javax.swing.JTable();
        pnlSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        pnlIconSearch = new javax.swing.JLabel();
        btnSxMa = new javax.swing.JButton();
        btnSxName = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        pnlQlnm = new javax.swing.JPanel();
        btnTrangChu1 = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnXuatEx = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnDark = new javax.swing.JButton();
        btnOffDark = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblManm.setText("Mã Người Mua:");

        lblDiachi.setText("Địa Chỉ:");

        lblTennm.setText("Tên Người Mua:");

        lblSdt.setText("Số Điện Thoại:");

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/first.png"))); // NOI18N
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

        btnnext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/next.png"))); // NOI18N
        btnnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnextActionPerformed(evt);
            }
        });

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/last.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblManv.setText("Mã Nhân Viên :");

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Add.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Edit.png"))); // NOI18N
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Refresh.png"))); // NOI18N
        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        txaSohuu.setColumns(20);
        txaSohuu.setRows(5);
        txaSohuu.setFocusable(false);
        jScrollPane2.setViewportView(txaSohuu);

        jLabel1.setText("Sở hữu");

        javax.swing.GroupLayout pnlUpdateLayout = new javax.swing.GroupLayout(pnlUpdate);
        pnlUpdate.setLayout(pnlUpdateLayout);
        pnlUpdateLayout.setHorizontalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtManm, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDiachi)
                    .addComponent(txtTennm)
                    .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtManv)
                    .addGroup(pnlUpdateLayout.createSequentialGroup()
                        .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTennm, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblManm, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblManv, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlUpdateLayout.createSequentialGroup()
                        .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnlUpdateLayout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNew))
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrev)
                        .addGap(18, 18, 18)
                        .addComponent(btnnext)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast)))
                .addContainerGap())
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlUpdateLayout.setVerticalGroup(
            pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblManm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtManm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDiachi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiachi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTennm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTennm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSdt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblManv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtManv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnnext))
                    .addGroup(pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdd)
                        .addComponent(btnDelete)
                        .addComponent(btnUpdate)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tabs.addTab("CẬP NHẬT", pnlUpdate);

        tblQlnm.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ NGƯỜI MUA", "ĐỊA CHỈ", "TÊN NGƯỜI MUA", "SỐ ĐIỆN THOẠI", "MÃ NHÂN VIÊN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblQlnm.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblQlnmAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblQlnm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblQlnmMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblQlnmMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblQlnm);

        pnlSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Kiếm", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        pnlIconSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Search.png"))); // NOI18N

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(pnlIconSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlIconSearch))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnSxMa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSxMa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/List.png"))); // NOI18N
        btnSxMa.setText("Sắp Xếp Theo Mã");
        btnSxMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSxMaActionPerformed(evt);
            }
        });

        btnSxName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSxName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/raven/icon/Numbered list.png"))); // NOI18N
        btnSxName.setText("Sắp Xếp Theo Tên");
        btnSxName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSxNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlListLayout = new javax.swing.GroupLayout(pnlList);
        pnlList.setLayout(pnlListLayout);
        pnlListLayout.setHorizontalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlListLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(btnSxName, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnSxMa, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                        .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        pnlListLayout.setVerticalGroup(
            pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlListLayout.createSequentialGroup()
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSxMa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSxName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(152, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", pnlList);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 204, 255));
        lblTitle.setText("QUẢN LÝ NGƯỜI MUA");

        pnlQlnm.setBackground(new java.awt.Color(232, 255, 255));

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

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/icons8-manage-100.png"))); // NOI18N

        btnDark.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/icons8-moon-30.png"))); // NOI18N
        btnDark.setText("Chế Độ Tối");
        btnDark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDarkMouseClicked(evt);
            }
        });
        btnDark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDarkActionPerformed(evt);
            }
        });

        btnOffDark.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnOffDark.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/icons8-moon-30.png"))); // NOI18N
        btnOffDark.setText("Tắt Chế Độ Tối");
        btnOffDark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnOffDarkMouseClicked(evt);
            }
        });
        btnOffDark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOffDarkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQlnmLayout = new javax.swing.GroupLayout(pnlQlnm);
        pnlQlnm.setLayout(pnlQlnmLayout);
        pnlQlnmLayout.setHorizontalGroup(
            pnlQlnmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQlnmLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQlnmLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlQlnmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOffDark, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTrangChu1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatEx, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDark, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlQlnmLayout.setVerticalGroup(
            pnlQlnmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQlnmLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(btnTrangChu1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXuatEx, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDark)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOffDark)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlQlnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tabs)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs)
                .addContainerGap())
            .addComponent(pnlQlnm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        currentIndex = 0;
        showDataAtIndex(currentIndex);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        if (currentIndex > 0) {
            currentIndex--;
            showDataAtIndex(currentIndex);
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
        if (currentIndex < tblQlnm.getRowCount() - 1) {
            currentIndex++;
            showDataAtIndex(currentIndex);
        }
    }//GEN-LAST:event_btnnextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        currentIndex = tblQlnm.getRowCount() - 1;
        showDataAtIndex(currentIndex);
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblQlnmAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblQlnmAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblQlnmAncestorAdded

    private void tblQlnmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQlnmMouseClicked
        selectRow();
    }//GEN-LAST:event_tblQlnmMouseClicked

    private void btnSxMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSxMaActionPerformed
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblQlnm.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort = 0; // Assuming "Mã Người Mua" is in the first column (change if needed)
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }//GEN-LAST:event_btnSxMaActionPerformed

    private void btnSxNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSxNameActionPerformed
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblQlnm.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort = 2; // Assuming "Tên Người Mua" is in the third column (change if needed)
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }//GEN-LAST:event_btnSxNameActionPerformed

    private void btnTrangChu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChu1ActionPerformed
       new MainJFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnTrangChu1ActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnXuatExActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExActionPerformed
        List<NguoiMua> nguoiMuaList = nguoiMuaDAO.getAllNguoiMua(); // Lấy danh sách người mua từ DAO
        String filePath = "C:\\Users\\ntqba\\OneDrive\\Desktop\\File_Excel"; // Thay đổi đường dẫn tùy theo nơi bạn muốn lưu file
        exportToExcel(nguoiMuaList, filePath);
    }//GEN-LAST:event_btnXuatExActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnDarkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDarkMouseClicked

    }//GEN-LAST:event_btnDarkMouseClicked

    private void btnDarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDarkActionPerformed
        updateTheme();
    }//GEN-LAST:event_btnDarkActionPerformed

    private void btnOffDarkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOffDarkMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOffDarkMouseClicked

    private void btnOffDarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOffDarkActionPerformed
        offupdateTheme();
    }//GEN-LAST:event_btnOffDarkActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String manm = txtManm.getText();
        String diachi = txtDiachi.getText();
        String tennm = txtTennm.getText();
        String sodt = txtSdt.getText();
        String manv = txtManv.getText();
        if (!isValidFormData(manm, diachi, tennm, sodt)) {
            return;
        }

        NguoiMua nguoiMua = new NguoiMua(manm, diachi, tennm, sodt, manv);

        if (nguoiMuaDAO.addNguoiMua(nguoiMua)) {
            JOptionPane.showMessageDialog(this, "Thêm người mua thành công");
            loadDataToTable();
            selectFirstRow();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm người mua thất bại");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int selectedRow = tblQlnm.getSelectedRow();
        if (selectedRow != -1) {
            String maNM = tblQlnm.getValueAt(selectedRow, 0).toString();
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa người mua này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                if (nguoiMuaDAO.deleteNguoiMua(maNM)) {
                    JOptionPane.showMessageDialog(this, "Xóa người mua thành công");
                    loadDataToTable();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa người mua thất bại");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người mua để xóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int selectedRow = tblQlnm.getSelectedRow();
        if (selectedRow != -1) {
            String maNM = txtManm.getText();
            String diachi = txtDiachi.getText();
            String tennm = txtTennm.getText();
            String sodt = txtSdt.getText();
            String maNV = txtManv.getText();

            if (!isValidFormData(maNM, diachi, tennm, sodt)) {
                return;
            }

            NguoiMua nguoiMua = new NguoiMua(maNM, diachi, tennm, sodt, maNV);

            if (nguoiMuaDAO.updateNguoiMua(nguoiMua)) {
                JOptionPane.showMessageDialog(this, "Cập nhật người mua thành công");
                loadDataToTable();
                selectRow();
                // Tìm vị trí của dòng vừa cập nhật
                int updatedRowIndex = findRowIndexByMaNM(maNM);

                // Chọn lại dòng vừa cập nhật trong bảng
                if (updatedRowIndex != -1) {
                    tblQlnm.setRowSelectionInterval(updatedRowIndex, updatedRowIndex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật người mua thất bại");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người mua để cập nhật", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearFields();
        txtManv.setText(ShareHelper.getUSER().getMaNV());
    }//GEN-LAST:event_btnNewActionPerformed

    private void tblQlnmMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQlnmMousePressed
        if (evt.getClickCount() == 2) {
            this.currentIndex = tblQlnm.rowAtPoint(evt.getPoint());
            selectRow();
        }
    }//GEN-LAST:event_tblQlnmMousePressed

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
            java.util.logging.Logger.getLogger(QuanlynguoimuaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanlynguoimuaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanlynguoimuaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanlynguoimuaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanlynguoimuaJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDark;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnOffDark;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSxMa;
    private javax.swing.JButton btnSxName;
    private javax.swing.JButton btnTrangChu1;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXuatEx;
    private javax.swing.JButton btnnext;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDiachi;
    private javax.swing.JLabel lblManm;
    private javax.swing.JLabel lblManv;
    private javax.swing.JLabel lblSdt;
    private javax.swing.JLabel lblTennm;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel pnlIconSearch;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlQlnm;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlUpdate;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblQlnm;
    private javax.swing.JTextArea txaSohuu;
    private javax.swing.JTextField txtDiachi;
    private javax.swing.JTextField txtManm;
    private javax.swing.JTextField txtManv;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTennm;
    // End of variables declaration//GEN-END:variables
private TableRowSorter<DefaultTableModel> rowSorter;

    private void filter() {
        String keyword = txtSearch.getText();
        if (keyword.trim().length() == 0) {
            rowSorter.setRowFilter(null); // Không có lọc nếu không có từ khóa
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword)); // Lọc dựa trên từ khóa (không phân biệt chữ hoa/thường)
        }
    }

    private void exportToExcel(List<NguoiMua> nguoiMuaList, String filePath) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("QLNM");

            for (int rowIndex = 0; rowIndex < nguoiMuaList.size(); rowIndex++) {
                Row row = sheet.createRow(rowIndex + 1);
                NguoiMua nguoiMua = nguoiMuaList.get(rowIndex);

                row.createCell(0).setCellValue(nguoiMua.getMaNM());
                row.createCell(1).setCellValue(nguoiMua.getTenNM());
                row.createCell(2).setCellValue(nguoiMua.getSoDt());
                row.createCell(3).setCellValue(nguoiMua.getDiaChi());
                row.createCell(4).setCellValue(nguoiMua.getMaNV());
            }

            // Đảm bảo đường dẫn đến thư mục tồn tại trong dự án
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdirs();  // Tạo thư mục nếu chưa tồn tại
            }

            Date ngayhientai = new Date();
            // Tạo tên tệp dựa trên thời gian hiện tại để tránh trùng lặp
            String fileName = "QLNM_" + new SimpleDateFormat("dd-MM-yyyy").format(ngayhientai) + ".xls";
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
}
