package admin;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailServices {
    public MailServices(String recepient, String sender, String pass, String subject, String text) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String senderAcc = sender;
        String password = pass;

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderAcc, password);
            }
        });

        Message message = prepareMessage(session, sender, recepient, subject, text);

        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String sender, String recepient, String subject, String text){
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(subject);
            message.setText(text);
            return message;
        }catch (Exception ex){
            Logger.getLogger(MailServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
