/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlbds.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import qlbds.model.NguoiMua;

/**
 *
 * @author Duy Linh
 */
public class NguoiMuaDAO {

    private Connection connection;
    private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=qlbds_nhom5;encrypt=false";
    private static String username = "sa";
    private static String password = "songlong";


    public NguoiMuaDAO() {
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
             connection = DriverManager.getConnection(dburl, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NguoiMua> getAllNguoiMua() {
        List<NguoiMua> nguoiMuaList = new ArrayList<>();

        try {
            String query = "SELECT * FROM NguoiMua";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String manm = resultSet.getString("Manm");
                String diachi = resultSet.getString("Diachi");
                String tennm = resultSet.getString("Tennm");
                String sodt = resultSet.getString("Sodt");
                String manv = resultSet.getString("Manv");

                NguoiMua nguoiMua = new NguoiMua(manm, diachi, tennm, sodt, manv);
                nguoiMuaList.add(nguoiMua);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nguoiMuaList;
    }

    public boolean addNguoiMua(NguoiMua nguoiMua) {
        try {
            String insertQuery = "INSERT INTO NguoiMua (Manm, Diachi, Tennm, Sodt, Manv) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, nguoiMua.getMaNM());
            preparedStatement.setString(2, nguoiMua.getDiaChi());
            preparedStatement.setString(3, nguoiMua.getTenNM());
            preparedStatement.setString(4, nguoiMua.getSoDt());
            preparedStatement.setString(5, nguoiMua.getMaNV());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateNguoiMua(NguoiMua nguoiMua) {
        String sql = "UPDATE nguoimua SET diaChi = ?, tenNM = ?, soDt = ?, maNV = ? WHERE maNM = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nguoiMua.getDiaChi());
            stmt.setString(2, nguoiMua.getTenNM());
            stmt.setString(3, nguoiMua.getSoDt());
            stmt.setString(4, nguoiMua.getMaNV());
            stmt.setString(5, nguoiMua.getMaNM());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteNguoiMua(String maNM) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
             conn = DriverManager.getConnection(dburl, username, password);

            // Trước tiên, kiểm tra xem có dữ liệu trong bảng DonHang liên quan đến người mua không
            if (hasDonHangForNguoiMua(conn, maNM)) {
                // Nếu có, thông báo và không thực hiện xóa
                JOptionPane.showMessageDialog(null, "Không thể xóa người mua có đơn hàng liên quan.");
                return false;
            }

            // Nếu không có đơn hàng liên quan, tiến hành xóa người mua
            String sql = "DELETE FROM NguoiMua WHERE MaNM=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, maNM);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasDonHangForNguoiMua(Connection conn, String maNM) throws SQLException {
        // Kiểm tra xem có đơn hàng liên quan đến người mua không
        String sql = "SELECT COUNT(*) FROM DonHang WHERE Manm=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maNM);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    // Đóng kết nối đến cơ sở dữ liệu
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NguoiMua> searchNguoiMua(String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<NguoiMua> result = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(dburl, username, password);

            // Tìm kiếm theo mã người mua hoặc tên người mua
            String sql = "SELECT * FROM NguoiMua WHERE MaNM LIKE ? OR TenNM LIKE ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String maNM = rs.getString("MaNM");
                String diaChi = rs.getString("DiaChi");
                String tenNM = rs.getString("TenNM");
                String soDt = rs.getString("SoDt");
                String maNV = rs.getString("MaNV");

                NguoiMua nguoiMua = new NguoiMua(maNM, diaChi, tenNM, soDt, maNV);
                result.add(nguoiMua);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    // Hàm hỗ trợ kiểm tra chuỗi có chứa từ khóa không (không phân biệt chữ hoa, chữ thường)
    private boolean containsIgnoreCase(String source, String keyword) {
        return source.toLowerCase().contains(keyword.toLowerCase());
    }
}
