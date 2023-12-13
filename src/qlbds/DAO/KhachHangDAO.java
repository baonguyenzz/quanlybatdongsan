/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlbds.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import qlbds.helper.JdbcHelper;
import qlbds.model.KhachHang;
import qlbds.model.NhanVien;

/**
 *
 * @author ADMIN
 */
public class KhachHangDAO {

    public void insert(KhachHang model) {
        String sql = "insert into KhachHang(Makh, Tenkh, Sodt, Diachi) values (?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                model.getTenKH(),
                model.getSoDt(),
                model.getDiaChi()
        );
    }

    public void update(KhachHang model) {
        String sql = "update KhachHang set Tenkh = ?, Sodt = ?, Diachi = ? where Makh = ?";
        JdbcHelper.executeUpdate(sql,
                model.getTenKH(),
                model.getSoDt(),
                model.getDiaChi(),
                model.getMaKH()
        );
    }

    public void delete(String makh) {
        String sql = "delete from KhachHang where Makh = ?";
        JdbcHelper.executeUpdate(sql, makh);
    }
    
    public KhachHang findById(String maKH) {
        String sql ="Select * from KhachHang where maKH = ?";
        List<KhachHang> list = select(sql, maKH);
        return list.size() > 0 ? list.get(0) : null;
    }

    public List<KhachHang> select() {
        String sql = "select * from KhachHang";
        return select(sql);

    }

    private List<KhachHang> select(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    KhachHang model = readFormResultSet(rs);
                    list.add(model);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private KhachHang readFormResultSet(ResultSet rs) throws SQLException {
        KhachHang model = new KhachHang();
        model.setMaKH(rs.getString("Makh"));
        model.setTenKH(rs.getString("Tenkh"));
        model.setSoDt(rs.getString("Sodt"));
        model.setDiaChi(rs.getString("Diachi"));
        return model;
    }

}
