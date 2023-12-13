/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlbds.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;
import qlbds.helper.JdbcHelper;
import qlbds.helper.ShareHelper;
import qlbds.model.BatDongSan;
import qlbds.model.DonHang;

/**
 *
 * @author ntqba
 */
public class DonHangDAO {

    public void insert(DonHang model) {
        String sql = "INSERT INTO donhang (madh,ngaygiaodich, mabds, manm,Manv) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaDH(),
                model.getNgayGD(),
                model.getMaBds(),
                model.getMaNM(),
                ShareHelper.getUSER().getMaNV()
        );

    }

    public String getLastId() {
        String sql = "select top 1 * from donhang\n"
                + "order by sothutu desc";
        List<DonHang> list = select(sql);
        if (list != null && !list.isEmpty()) {
            String id = list.get(0).getMaDH();
            return id;
        }
        return null;
    }

    public void delete(String Madh) {
        String sql = "DELETE FROM donhang WHERE madh=?";
        JdbcHelper.executeUpdate(sql, Madh);
    }

    public List<DonHang> select() {
        String sql = "SELECT * FROM donhang";
        return select(sql);
    }

    public List<Pair<DonHang, BatDongSan>> selectDetail() {
        String sql = "SELECT * FROM donhang";
        return selectDetail(sql);
    }

    public List<DonHang> select(String sql, Object... args) {
        List<DonHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    DonHang model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<Pair<DonHang, BatDongSan>> selectDetail(String sql, Object... args) {
        List<Pair<DonHang, BatDongSan>> ListP = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    ListP.add(DetailInfo(rs));
                }

            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return ListP;
    }

    public DonHang readFromResultSet(ResultSet rs) throws SQLException {
        DonHang model = new DonHang();
        model.setMaDH(rs.getString("Madh"));
        model.setNgayGD(rs.getDate("Ngaygiaodich"));
        model.setMaBds(rs.getString("Mabds"));
        model.setMaNM(rs.getString("Manm"));
        return model;
    }

    //
    public Pair<DonHang, BatDongSan> DetailInfo(ResultSet rs) throws SQLException {
        DonHang dh = readFromResultSet(rs);
        String id = dh.getMaBds();
        BatDongSan bds = new BatDongSanDAO().FindbyIdBds(id);

        return new Pair<>(dh, bds);
    }

    //ham nay dùng để đưa lên table
    public List<Object[]> ObjectDetailInfo(List<Pair<DonHang, BatDongSan>> list) throws SQLException {
        List<Object[]> listO = new ArrayList<>();
        for (Pair<DonHang, BatDongSan> p : list) {
            DonHang dh = p.getKey();
            BatDongSan bds = p.getValue();
            Object[] O = {dh.getMaDH(), dh.getNgayGD(), dh.getMaBds(), dh.getMaNM(), bds.getMaKH(), bds.getTenBds(), bds.getDienTich(), bds.getLoaiBds(), bds.getGiaBds(), bds.getHoaHong()};
            listO.add(O);
        }
        return listO;
    }
}
