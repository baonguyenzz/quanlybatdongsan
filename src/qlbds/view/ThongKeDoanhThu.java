/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlbds.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.plot.PlotOrientation;
import qlbds.helper.ShareHelper;

public class ThongKeDoanhThu extends javax.swing.JFrame {

    public ThongKeDoanhThu(int year) {
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createChart(year);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biểu Đồ Doanh Thu BĐS Theo Loại Hình");
 setIconImage(ShareHelper.getAppIcon());
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        pack();
    }

    private void createChart(int year) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "DOANH THU THEO LOẠI HÌNH BẤT ĐỘNG SẢN",
                "Loại Hình Bất Động Sản", "Doanh Thu",
                createDataset(year), PlotOrientation.VERTICAL, false, false, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
        setSize(750, 450);
        setLocationRelativeTo(null);
    }
//    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//    static {
//        try {
//            Class.forName(driver);
//        } catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
    private CategoryDataset createDataset(int year) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        

        String dburl = "jdbc:sqlserver://localhost:1433;databaseName=qlbds_nhom5;encrypt=false";
        String username = "sa";
        String password = "songlong";
        try {
            // Kết nối cơ sở dữ liệu
             
                 
            Connection connection = DriverManager.getConnection(dburl, username, password);

            // Chuẩn bị truy vấn với tham số
            String query = "{CALL sp_DoanhThu(?)}";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, year);

                // Thực hiện truy vấn
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String loaiHinh = resultSet.getString("LoaiHinh");
                        double doanhThu = resultSet.getDouble("DoanhThu");

                        // Thêm dữ liệu vào dataset
                        dataset.addValue(doanhThu, "DoanhThu", loaiHinh);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }


}




