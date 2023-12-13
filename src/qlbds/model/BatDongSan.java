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
public class BatDongSan implements Serializable {
    private String maBds;
    private String tenBds;
    private String loaiBds;
    private String diaChi;
    private float dienTich;
    private float giaBds;
    private float hoaHong;
    private String maKH;
    private String maNV;
    byte[] hinh;

    @Override 
   public String toString() { 
       return this.tenBds; 
  
    }
    public BatDongSan() {
    }

    public BatDongSan(String maBds, String tenBds, String loaiBds, String diaChi, float dienTich, float giaBds, float hoaHong, String maKH, String maNV) {
        this.maBds = maBds;
        this.tenBds = tenBds;
        this.loaiBds = loaiBds;
        this.diaChi = diaChi;
        this.dienTich = dienTich;
        this.giaBds = giaBds;
        this.hoaHong = hoaHong;
        this.maKH = maKH;
        this.maNV = maNV;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }


    public String getMaBds() {
        return maBds;
    }

    public void setMaBds(String maBds) {
        this.maBds = maBds;
    }

    public String getTenBds() {
        return tenBds;
    }

    public void setTenBds(String tenBds) {
        this.tenBds = tenBds;
    }

    public String getLoaiBds() {
        return loaiBds;
    }

    public void setLoaiBds(String loaiBds) {
        this.loaiBds = loaiBds;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public float getDienTich() {
        return dienTich;
    }

    public void setDienTich(float dienTich) {
        this.dienTich = dienTich;
    }

    public float getGiaBds() {
        return giaBds;
    }

    public void setGiaBds(float giaBds) {
        this.giaBds = giaBds;
    }

    public float getHoaHong() {
        return hoaHong;
    }

    public void setHoaHong(float hoaHong) {
        this.hoaHong = hoaHong;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    

   
    
    
}
