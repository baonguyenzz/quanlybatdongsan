/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package SanPhamBDS;

import Utility.UtilityImage;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import qlbds.model.BatDongSan;
import qlbds.view.QuanlydonhangJFrame;

/**
 *
 * @author baont
 */
public class SanPham extends javax.swing.JPanel {

    int index;
    BatDongSan bds;
    JFrame qldatmua;

    /**
     * Creates new form SanPham
     */
    public SanPham(BatDongSan bds, int i, JFrame qldatmua) {
        initComponents();
        lblHinh.setSize(275, 138);
        this.bds = bds;
        this.index = i;
        lblGia.setText(String.valueOf(bds.getGiaBds()));
        lblMa.setText(bds.getMaBds());
        lblTen.setText(bds.getTenBds());
        lblTen.setToolTipText(bds.getTenBds());
        txtDientich.setText(String.valueOf(bds.getDienTich()));
        lblLoaiHinh.setText(bds.getLoaiBds());
        if (bds.getHinh() != null) {
            ImageIcon icon=UtilityImage.BytesToImageIcon(bds.getHinh());
            lblHinh.setIcon(Utility.UtilityImage.ResizeHinh(lblHinh, icon));
        }

        this.qldatmua = qldatmua;
    }

    public int getIndex() {
        return index;
    }

    public void addButtonLen() {
        pnlButton.add(btnLendonhang, BorderLayout.CENTER);

    }

    public BatDongSan getBds() {
        return bds;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnLendonhang = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        lblGia = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMa = new javax.swing.JTextField();
        lblTen = new javax.swing.JTextField(10);
        lblLoaiHinh = new javax.swing.JTextField();
        pnlButton = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtDientich = new javax.swing.JTextField();

        btnLendonhang.setText("Lên đơn hàng");
        btnLendonhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLendonhangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLendonhangMouseEntered(evt);
            }
        });

        setBackground(new java.awt.Color(204, 255, 204));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Giá");

        lblHinh.setMaximumSize(new java.awt.Dimension(275, 138));
        lblHinh.setMinimumSize(new java.awt.Dimension(275, 138));

        lblGia.setBackground(new java.awt.Color(70, 73, 75));
        lblGia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Mã bất động sản");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Tên bất động sản");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Loại hình bất động sản");

        lblMa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblMa.setFocusable(false);

        lblTen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblTen.setFocusable(false);

        lblLoaiHinh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblLoaiHinh.setFocusable(false);

        pnlButton.setBackground(new java.awt.Color(204, 204, 255));
        pnlButton.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Diện tích");

        txtDientich.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtDientich.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblHinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(pnlButton, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDientich)
                            .addComponent(lblMa, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(lblTen)
                            .addComponent(lblLoaiHinh)
                            .addComponent(lblGia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblGia, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblLoaiHinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDientich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLendonhangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLendonhangMouseClicked
        new QuanlydonhangJFrame(bds, qldatmua).setVisible(true);

    }//GEN-LAST:event_btnLendonhangMouseClicked

    private void btnLendonhangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLendonhangMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLendonhangMouseEntered


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLendonhang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTextField lblLoaiHinh;
    private javax.swing.JTextField lblMa;
    private javax.swing.JTextField lblTen;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JTextField txtDientich;
    // End of variables declaration//GEN-END:variables
}
