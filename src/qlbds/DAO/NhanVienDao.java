/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlbds.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import qlbds.model.NhanVien;
import qlbds.helper.JdbcHelper;

/**
 *
 * @author LENOVO
 */
import java.util.List;

public class NhanVienDao {

    public void insert(NhanVien model) {
        String sql = "INSERT INTO nhanvien (Manv, Matkhau, tennv, Diachi, Sodt, Email, Vaitro) VALUES (?, ?, ?, ?, ?,?,?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaNV(),
                model.getMatKhau(),
                model.getHoTen(),
                model.getDiaChi(),
                model.getSoDt(),               
                model.getEmail(),
                model.isVaiTro());
    }

    public void update(NhanVien model) {
        String sql = "UPDATE nhanvien SET Matkhau=?, tennv=?, Diachi=?, Sodt=?, Email=?, Vaitro=? WHERE Manv=?";
        JdbcHelper.executeUpdate(sql,
                model.getMatKhau(),
                model.getHoTen(),
                model.getDiaChi(),
                model.getSoDt(),
                model.getEmail(),
                model.isVaiTro(),
                model.getMaNV());
    }

    public void delete(String MaNV) {
        String sql = "DELETE FROM nhanvien WHERE Manv=?";
        JdbcHelper.executeUpdate(sql, MaNV);
    }

    public List<NhanVien> select() {
        String sql = "SELECT * FROM Nhanvien";
        return select(sql);
    }

    public NhanVien findById(String manv) {
        String sql = "SELECT * FROM Nhanvien WHERE Manv=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    } // thay bằng hàm bên dưới
public NhanVien findByIdAndPass(String manv, String pass) {
        String sql = "SELECT * FROM Nhanvien WHERE Manv=? and Matkhau = ?";
        List<NhanVien> list = select(sql, manv,pass);
        return list.size() > 0 ? list.get(0) : null;
    }
//   private List<NhanVien> select(String sql, Object... args) {
//        List<NhanVien> list = new ArrayList<>();
//        try {
//            ResultSet rs = null;
//            try {
//                rs = JdbcHelper.executeQuery(sql, args);
//                while (rs.next()) {
//                    NhanVien model = readFromResultSet(rs);
//                    list.add(model);
//                }
//            } finally {
//                rs.getStatement().getConnection().close();
//            }
//        } catch (SQLException ex) {
//            throw new RuntimeException(ex);
//        }
//        return list;
//    }
private List<NhanVien> select(String sql, Object... args) {
    List<NhanVien> list = new ArrayList<>();
    ResultSet rs = null;

    try {
        rs = JdbcHelper.executeQuery(sql, args);

        while (rs.next()) {
            NhanVien model = readFromResultSet(rs);
            list.add(model);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        throw new RuntimeException(ex);
    } finally {
        if (rs != null) {
            try {
                rs.getStatement().close();
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    return list;
}

    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien model = new NhanVien();
        model.setMaNV(rs.getString("Manv"));
        model.setMatKhau(rs.getString("Matkhau"));
        model.setHoTen(rs.getString("Tennv"));
        model.setVaiTro(rs.getBoolean("Vaitro"));
        model.setDiaChi(rs.getString("Diachi"));
        model.setSoDt(rs.getString("Sodt"));
        model.setEmail(rs.getString("Email"));
        return model;
    }
}
