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

/**
 *
 * @author ntqba
 */
public class ThongKeDAO {
        public List<Object[]> ThongKeDoanhThu(int nam) {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;           
            String sql = "{call sp_DoanhThu ("+nam+")}";
            rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getNString("LoaiHinh"),
                        rs.getInt("SoDH"),
                        rs.getFloat("DoanhThu"),
                        rs.getFloat("ThapNhat"),
                        rs.getFloat("CaoNhat"),
                        rs.getFloat("TrungBinh")
                    };
                    list.add(model);
                };
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }
}
