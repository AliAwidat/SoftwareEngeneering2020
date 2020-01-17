package src.lil.common;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//lilach ltd 2-factor login
//        5817 7897
//        9681 5347
//        7515 4168
//        1044 4376
//        1550 0375
//        8347 6608
//        4141 5934
//        3934 3809
//        7009 2146
//        7757 9307
public class sendMail {

    public static void main(String [] args) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", "lilach.ltd");
        props.put("mail.smtp.password", "");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            String from="lilach.ltd@gmail.com";
            String[] gmail2={"z.basel324@gmail.com","miraqi147@gmail.com"};
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[gmail2.length];

            // To get the array of addresses
            for( int i = 0; i < gmail2.length; i++ ) {
                toAddress[i] = new InternetAddress(gmail2[i]);
            }

            for (InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            message.setSubject("Delivery in Time");
            message.setText("Dear Majd, Delivery has been in time");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, "lilach.ltd@gmail.com", "umsrnjzmyvmkttyh");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ae) {
            ae.printStackTrace();
        }
    }
}