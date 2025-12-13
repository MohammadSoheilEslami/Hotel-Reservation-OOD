package services;

public class OnlinePayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Paid Online (Card/PayPal): " + amount);
    }
}