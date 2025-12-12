package services;

import constants.Notifier;
import constants.PaymentMethods;
import models.Reservation;

public class ReservationService {
    private Notifier notifier = Notifier.EMAIL; 
    private PaymentProcessor paymentProcessor = new PaymentProcessor();
    
    // ۱. این خط اضافه شده: ابزار پیامک را آماده کن
    private SmsSender smsSender = new SmsSender();

    public void makeReservation(Reservation res, PaymentMethods paymentType, Notifier notifier){
        System.out.println("Processing reservation for " + res.customer.name);

        if(res.customer.city.equals("Paris")){
            System.out.println("Apply city discount for Paris!");
            res.room.price *= 0.9;
        }

        switch (paymentType){
            case CARD:
                paymentProcessor.payByCard(res.totalPrice());
                break;
            case PAYPAL:
                paymentProcessor.payByPayPal(res.totalPrice());
                break;
            case CASH:
                paymentProcessor.payByCash(res.totalPrice());
                break;
            // هنوز پرداخت حضوری را اضافه نکردیم (چون وظیفه هم‌گروهی است)
        }

        System.out.println("----- INVOICE -----");
        System.out.println("Customer: " + res.customer.name);
        System.out.println("Room: " + res.room.number + " (" + res.room.type + ")");
        System.out.println("Total: " + res.totalPrice());
        System.out.println("-------------------");

       // ۲. این قسمت تغییر کرده: شرط SMS اضافه شد
       switch (notifier){
           case EMAIL :
               EmailSender emailSender = new EmailSender();
               emailSender.sendEmail(res.customer.email, "Your reservation confirmed!");
               break;
           case SMS:
               // اگر SMS بود، پیامک بزن
               smsSender.sendSms(res.customer.phoneNumber, "Your reservation confirmed!");
               break;
           default:
               System.out.println("There is no Message Provider");
       }
    }
}