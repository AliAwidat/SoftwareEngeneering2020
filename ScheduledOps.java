import src.lil.common.DBConnection;
import src.lil.models.Client;
import src.lil.models.Complain;
import src.lil.models.User;
import src.lil.common.sendMail;
import java.sql.*;
import java.util.ArrayList;

public class ScheduledOps {
    public static void messageAboutComplain() {
        ArrayList<Complain> complains = null;
        try{
            String contact_email,contact_phone,complain_title,complain_text,store_adress,order_Id, user_id;
            int complain_id;
            Date date;
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM complains where complain_closed=0 and complain_date <= now() - INTERVAL 1 DAY";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                complain_id=rs.getInt("complain_id");
                contact_email=rs.getString("contact_email");
                contact_phone=rs.getString("contact_phone");
                complain_title=rs.getString("complain_title");
                complain_text=rs.getString("complain_text");
                store_adress=rs.getString("store_adress");
                order_Id=rs.getString("order_Id");
                user_id=rs.getString("client_id");
                date=rs.getDate("complain_date");
                complains.add(new Complain(complain_id,contact_email,contact_phone, complain_title, complain_text, store_adress, date,order_Id,user_id));
            }
            connection.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        ArrayList<String> customersEmails = new ArrayList<>();
        if (complains == null) {
            return;
        }
        for (Complain complain : complains) {
            if (complain != null) {
                customersEmails.add(complain.getContact_email());
                        String emailSubject = "Status about complain " + complain.getComplainId();
                        String emailBody =
                                "Hello :)\n We're sorry to tell you that our customers service is still working on your complain  \n\n Thank you and best regards\nLilach LTD";
                        new sendMail(customersEmails,emailSubject,emailBody);
                }
                customersEmails.remove(0);
            }
    }
}
