/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlbds.model;

import java.io.Serializable;

/**
 *
 * @author baont
 */
public class KhachHang implements Serializable {
    private String maKH;
    private String tenKH;
    private String soDt;
    private String diaChi;
    
    @Override 
   public String toString() { 
       return this.tenKH; 
    }

    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String soDt, String diaChi) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soDt = soDt;
        this.diaChi = diaChi;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
   
}
