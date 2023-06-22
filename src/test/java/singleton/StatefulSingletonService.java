package singleton;

public class StatefulSingletonService {

    private int price;

    public void order(String user, int price) {
        this.price = price;
        System.out.printf("[%s] 주문 금액 : %d\n", user, price);
    }

    public int getPrice() {
        return price;
    }
}
