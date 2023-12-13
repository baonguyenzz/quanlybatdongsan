/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger.Level;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author ADMIN
 */
public class UtilityImage {

    public UtilityImage() {

    }

    public static String LayTenHinh(String Hinh) {
        return new File(Hinh).getName();
    }

    public static ImageIcon ResizeHinh(JLabel lbl, String Hinh) {
        File c = new File(Hinh);
        String t = c.getAbsolutePath();
        ImageIcon ha = new ImageIcon(t);
        Image img = ha.getImage();
        if (img == null) {
            System.out.println("Không thể tải hình ảnh: " + Hinh);
            return null;
        }
        Image newI = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newI);
    }

    public static ImageIcon ResizeHinh(JLabel lbl, ImageIcon Hinh) {      
        Image img = Hinh.getImage();
        if (img == null) {
            System.out.println("Không thể tải hình ảnh: " + Hinh);
            return null;
        }
        Image newI = img.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newI);
    }

    public static ImageIcon ResizeHinhTheoPreferrsize(JLabel lbl, String Hinh) {
        File c = new File(Hinh);
        String t = c.getAbsolutePath();
        ImageIcon ha = new ImageIcon(t);
        Image img = ha.getImage();
        if (img == null) {
            System.out.println("Không thể tải hình ảnh: " + Hinh);
            return null;
        }
        Image newI = img.getScaledInstance((int) lbl.getPreferredSize().getWidth(), (int) lbl.getPreferredSize().getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(newI);
    }

    public static String ChoosePathImage(String Duongdangoc) {
        String filePath = "";
        JFileChooser fileChooser = new JFileChooser(Duongdangoc);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //chỉ chọn file không chọn tệp
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "bmp"));//phân loại hình
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            filePath = fileChooser.getSelectedFile().getPath();
        }
        return filePath;
    }

    public static byte[] imageIconToByteArray(ImageIcon icon) {
        byte[] imageData = null;
        if (icon != null) {
            BufferedImage bufferedImage = new BufferedImage(
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );

            // Vẽ ImageIcon vào BufferedImage
            Graphics g = bufferedImage.getGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();

            // Chuyển đổi BufferedImage thành mảng byte
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(bufferedImage, "png", baos);
                imageData = baos.toByteArray();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return imageData;
    }

    public static ImageIcon BytesToImageIcon(byte[] imageData) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
            Image image = ImageIO.read(byteArrayInputStream);
            return new ImageIcon(image);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
