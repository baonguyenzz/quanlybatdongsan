/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlbds.model;

import java.util.Date;

/**
 *
 * @author baont
 */
public class DonHang {
    private String maDH;
    private Date ngayGD;
    private String maBds;
    private String maNM;
    private String maNV;

    public DonHang() {
    }

    public DonHang(String maDH, Date ngayGD, String maBds, String maNM, String maNV) {
        this.maDH = maDH;
        this.ngayGD = ngayGD;
        this.maBds = maBds;
        this.maNM = maNM;
        this.maNV = maNV;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public Date getNgayGD() {
        return ngayGD;
    }

    public void setNgayGD(Date ngayGD) {
        this.ngayGD = ngayGD;
    }

    public String getMaBds() {
        return maBds;
    }

    public void setMaBds(String maBds) {
        this.maBds = maBds;
    }

    public String getMaNM() {
        return maNM;
    }

    public void setMaNM(String maNM) {
        this.maNM = maNM;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    
    
    
}
