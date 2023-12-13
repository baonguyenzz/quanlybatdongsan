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
public class NguoiMua implements Serializable {
    private String maNM;
    private String diaChi;
    private String tenNM;
    private String soDt;
    private String maNV;
    
    
    @Override 
   public String toString() { 
       return this.tenNM; 
    }

    public NguoiMua() {
    }

    public NguoiMua(String maNM, String diaChi, String tenNM, String soDt, String maNV) {
        this.maNM = maNM;
        this.diaChi = diaChi;
        this.tenNM = tenNM;
        this.soDt = soDt;
        this.maNV = maNV;
    }

    public String getMaNM() {
        return maNM;
    }

    public void setMaNM(String maNM) {
        this.maNM = maNM;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTenNM() {
        return tenNM;
    }

    public void setTenNM(String tenNM) {
        this.tenNM = tenNM;
    }

    public String getSoDt() {
        return soDt;
    }

    public void setSoDt(String soDt) {
        this.soDt = soDt;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    

   
}
