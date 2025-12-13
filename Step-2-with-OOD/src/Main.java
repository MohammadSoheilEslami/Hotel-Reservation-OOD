import models.Customer;
import models.LuxuryRoom;
import models.Reservation;
import models.Room;
import services.*;

public class Main {
    public static void main(String[] args){
        Customer customer = new Customer("Ali", "ali@example.com","09121112222", "Tehran");
        Room room = new LuxuryRoom("203", 150);
        Reservation res = new Reservation(room, customer, 2);

        // اینجا مشخص میکنیم از چه روشی میخواهیم استفاده کنیم
        // مثلا: پرداخت حضوری + پیامک
        PaymentMethod myPayment = new OnSitePayment();
        MessageSender mySender = new SmsSender();

        // تزریق وابستگی‌ها به سرویس
        ReservationService service = new ReservationService(mySender, myPayment);
        
        service.makeReservation(res);
    }
}