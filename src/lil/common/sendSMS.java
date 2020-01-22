//package src.lil.common;
//
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.Twilio;
//import com.twilio.type.PhoneNumber;
//
////import com.twilio.sdk.api.v2010.account.Message;
////import com.twilio.sdk.type.PhoneNumber;
//public class sendSMS {
//    // Find your Account Sid and Token at twilio.com/user/account
//    public static final String ACCOUNT_SID = "AC1e36802c0183375d7e34a6e32b8a8296";
//    public static final String AUTH_TOKEN = "4fb40d274e6a097604732a9f59892936";
//
//    public static void main(String[] args) {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//        PhoneNumber phoneNumberFrom = new PhoneNumber("+16235524173");
//        PhoneNumber phoneNumber2 = new PhoneNumber("+972527333603");
//        Message message = Message.creator(phoneNumber2,phoneNumberFrom, "Hello Basel , your order have been delivered successfully").create();
//
//        System.out.println(message.getSid());
//    }
//}
