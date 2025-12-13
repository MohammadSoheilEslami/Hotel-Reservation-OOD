package services;

import models.Reservation;

public class ReservationService {
    // رعایت اصل DIP: وابستگی به اینترفیس‌ها به جای کلاس‌های واقعی
    private final MessageSender messageSender;
    private final PaymentMethod paymentMethod;

    // تزریق وابستگی (Dependency Injection) از طریق سازنده
    public ReservationService(MessageSender messageSender, PaymentMethod paymentMethod) {
        this.messageSender = messageSender;
        this.paymentMethod = paymentMethod;
    }

    public void makeReservation(Reservation res) {
        // اعمال تخفیف
        if(res.customer.city.equals("Paris")){
            res.room.price *= 0.9;
        }

        // ۱. پرداخت (بدون سوییچ کیس!)
        paymentMethod.pay(res.totalPrice());

        System.out.println("----- INVOICE -----");
        System.out.println("Customer: " + res.customer.name);
        System.out.println("Total: " + res.totalPrice());
        System.out.println("-------------------");

        // ۲. ارسال پیام (بدون سوییچ کیس!)
        // فرض میکنیم پیام به ایمیل ارسال میشود (یا لاجیک انتخاب شماره/ایمیل را اینجا میگذاریم)
        String contact = res.customer.email;
        if(messageSender instanceof SmsSender) contact = res.customer.phoneNumber;
        
        messageSender.send(contact, "Your reservation confirmed!");
    }
}