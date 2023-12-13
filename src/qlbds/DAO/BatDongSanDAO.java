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
import qlbds.model.BatDongSan;

/**
 *
 * @author baont
 */
public class BatDongSanDAO {

    public void updateHinh(BatDongSan model) {
        System.out.println(model.getMaBds());
        String sql = "UPDATE batdongsan SET hinh=? WHERE mabds=?";
        JdbcHelper.executeUpdate(sql,
                model.getHinh(),
                model.getMaBds()
        );
    }

    public String getLastId() {
        String sql = "select top 1 * from BatDongSan\n"
                + "order by sothutu_bds desc";
        List<BatDongSan> list = select(sql);
        if(list !=null && !list.isEmpty()){
            String id=list.get(0).getMaBds();
            return id;
        }
        return null;
    }

    public void insert(BatDongSan model) {
        String sql = "INSERT INTO batdongsan (Mabds,tenbds, loaihinhbds, diachi, dientich, giabds, hoahong, makh, manv) VALUES ( ?,?, ?, ?, ?, ?, ?, ?, ?)";

        JdbcHelper.executeUpdate(sql,
                model.getMaBds(),
                model.getTenBds(),
                model.getLoaiBds(),
                model.getDiaChi(),
                model.getDienTich(),
                model.getGiaBds(),
                model.getHoaHong(),
                model.getMaKH(),
                model.getMaNV()
        );
        if (model.getHinh() != null) {
            
            updateHinh(model);
        }
    }

    public void update(BatDongSan model) {
        String sql = "UPDATE batdongsan SET tenbds=?, loaihinhbds=?, diachi=?, dientich=?, giabds=?, hoahong=?, makh=?, manv=? WHERE mabds=?";
        JdbcHelper.executeUpdate(sql,
                model.getTenBds(),
                model.getLoaiBds(),
                model.getDiaChi(),
                model.getDienTich(),
                model.getGiaBds(),
                model.getHoaHong(),
                model.getMaKH(),
                model.getMaNV(),
                model.getMaBds()
        );
        if (model.getHinh() != null) {
            
            updateHinh(model);
        }
    }

    public void delete(String Mabds) {
        String sql = "DELETE FROM batdongsan WHERE mabds=?";
        JdbcHelper.executeUpdate(sql, Mabds);
    }

    public List<BatDongSan> select() {
        String sql = "select * from BatDongSan";
        return select(sql);
    }

    public List<BatDongSan> selectDatMua() {
        String sql = """
                     select * from BatDongSan bds
                     where bds.Mabds not in (select Mabds from DonHang)""";
        return select(sql);
    }

    public List<BatDongSan> FindDatMua(String find) {
        String sql = """
                     select * from BatDongSan bds
                     where bds.Mabds not in (select Mabds from DonHang) and Tenbds like N'%""" + find + "%' or Mabds like '" + find + "' or Diachi like N'%" + find + "%' ";
        return select(sql);
    }

    public List<BatDongSan> FindLoaiDatMua(String find) {
        String sql = """
                     select * from BatDongSan bds
                     where bds.Mabds not in (select Mabds from DonHang) and loaihinhbds like N'""" + find + "'";
        return select(sql);
    }

    public BatDongSan FindbyIdBds(String id) {
        String sql = "SELECT * FROM batdongsan where mabds like '" + id + "'";
        List<BatDongSan> list = select(sql);
        if (list.isEmpty()) {
            return new BatDongSan();
        } else {
            return list.get(0);
        }
    }

    public List<BatDongSan> select(String sql, Object... args) {
        List<BatDongSan> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    BatDongSan model = readFromResultSet(rs);
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

    public List<BatDongSan> BatDongSanTheoKhoangGia(int thap, int cao) {
        String sql = "select * from BatDongSan where Giabds between " + thap + " and " + cao + "";
        return select(sql);
    }

    public List<BatDongSan> BatDongSanTheoLoai(String loai) {
        String sql = "select * from BatDongSan where loaihinhbds like N'%" + loai + "%'";
        return select(sql);
    }

    public List<BatDongSan> selectTheoTenMaDiaCHi(String find) {
        String sql = """
                     select * from BatDongSan
                     where Tenbds like N'%""" + find + "%' or Mabds like '" + find + "' or Diachi like N'%" + find + "%' ";
        return select(sql);
    }

    public List<BatDongSan> BatDongSanCaoDenThap() {
        String sql = "select * from BatDongSan\n"
                + "order by Giabds desc";
        return select(sql);
    }

    public List<BatDongSan> BatDongSanThapDenCao() {
        String sql = "select * from BatDongSan\n"
                + "order by Giabds asc";
        return select(sql);
    }

    public List<BatDongSan> BatDongSanCaoDenThap(String type) {
        String sql = "select * from BatDongSan where Loaihinhbds like N'%" + type + "%'\n"
                + "order by Giabds desc";
        return select(sql);
    }

    public List<BatDongSan> BatDongSanThapDenCao(String type) {
        String sql = "select * from BatDongSan where Loaihinhbds like N'%" + type + "%'\n"
                + "order by Giabds asc";
        return select(sql);
    }

    public BatDongSan readFromResultSet(ResultSet rs) throws SQLException {
        BatDongSan model = new BatDongSan();
        model.setMaBds(rs.getString("Mabds"));
        model.setTenBds(rs.getNString("Tenbds"));
        model.setLoaiBds(rs.getNString("Loaihinhbds"));
        model.setDiaChi(rs.getNString("Diachi"));
        model.setDienTich(rs.getFloat("Dientich"));
        model.setGiaBds(rs.getFloat("Giabds"));
        model.setHoaHong(rs.getFloat("Hoahong"));
        model.setMaKH(rs.getString("Makh"));
        model.setMaNV(rs.getString("Manv"));
        model.setHinh(rs.getBytes("Hinh"));
        return model;
    }
//ham lay danh sach bds cua nguoi mua

    public List<BatDongSan> SelectBdsNguoimua(String id) {
        String sql = "select BatDongSan.* from NguoiMua join DonHang on NguoiMua.Manm = DonHang.Manm\n"
                + "join BatDongSan on DonHang.Mabds = BatDongSan.Mabds\n"
                + "where NguoiMua.Manm like '" + id + "'";
        return select(sql);
    }
}
