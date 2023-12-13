/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbds.model;

/**
 *
 * @author Đăng Ngô
 */
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class NhanVien implements Serializable{
     private String maNV; 
     private String matKhau;
     private String hoTen;
     private boolean vaiTro = false; 
     private String diaChi;
     private String soDt;
     private String email;
     
     
@Override 
   public String toString() { 
       return this.hoTen; 
  
    }

    public NhanVien() {
    }

    public NhanVien(String maNV, String matKhau, String hoTen, String diaChi, String soDt, String email) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.soDt = soDt;
        this.email = email;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
    
}
