/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlbds.helper;

import qlbds.model.NhanVien;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author Admin
 */
public class ShareHelper {

    //ảnh biểu tượng của ứng dụng, xuất hiện trên mọi cửa sổ
  
   public static final Image APP_ICON;

    static {         // Tải biểu tượng ứng dụng
        String file = "";
        APP_ICON = new ImageIcon(ShareHelper.class.getResource(file)).getImage();
    }

     public static  Image getAppIcon(){
        URL url = ShareHelper.class.getResource("/raven/icon/bds.jpg");
        return new ImageIcon(url).getImage();
    }

    public static boolean saveLogo(File file) {
        File dir = new File("src//image");
        // Tạo thư mục nếu chưa tồn tại  
        if (!dir.exists()) {
            dir.mkdirs();//phương thức tạo thư mục
        }
        File newFile = new File(dir, file.getName());
        try {
// Copy vào thư mục logos (đè nếu đã tồn tại)
            Path source = Paths.get(file.getAbsolutePath());
            Path destination = Paths.get(newFile.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /*    Đọc hình ảnh logo chuyên đề 
 * @param fileName  là tên file logo 
 * @return ảnh đọc được
     */
    public static ImageIcon readLogo(String fileName) {
        File path = new File("src//image", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
    /*      * Đối tượng này chứa thông tin người sử dụng sau khi đăng nhập
     */   
    public static NhanVien USER = null;

    /*       Xóa thông tin của người sử dụng khi có yêu cầu đăng xuất 
     */ 
    public static void logoff() {
        ShareHelper.USER = null;
    }

    /*    
 * Kiểm tra xem đăng nhập hay chưa 
 * @return đăng nhập hay chưa
     */ 
    public static boolean authenticated() {
        return ShareHelper.USER != null;
    }
    
    public static boolean isManager() {
        return ShareHelper.authenticated()&& USER.isVaiTro();
    }

    public static NhanVien getUSER() {
        return USER;
    }
     public static boolean isLogin() {
        return ShareHelper.USER != null;
    }
}
