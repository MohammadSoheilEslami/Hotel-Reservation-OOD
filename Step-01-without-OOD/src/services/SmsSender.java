package services;

public class SmsSender {
    public void sendSms(String phoneNumber, String message){
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}