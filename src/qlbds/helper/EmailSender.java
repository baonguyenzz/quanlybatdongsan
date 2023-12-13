/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package qlbds.helper;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author baont
 */

public class EmailSender {
     private static final String senderEmail = "bao.ntq1991@gmail.com"; // Thay thế bằng email của bạn
    private static final String senderPassword = "mcfp jigk jhrg jobh"; // Thay thế bằng mật khẩu của bạn

    public static boolean sendEmail(String recipientEmail, String newPassword) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Thay thế bằng máy chủ SMTP tương ứng nếu không sử dụng Gmail
        properties.put("mail.smtp.port", "587"); // Thay thế bằng cổng SMTP tương ứng
        properties.put("mail.smtp.ssl.trust", "*");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Mật khẩu mới của bạn");
            message.setText("Mật khẩu mới của bạn là: " + newPassword);

            Transport.send(message);
            System.out.println("Đã gửi email thành công.");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace(); return false;
        }
    }
}
