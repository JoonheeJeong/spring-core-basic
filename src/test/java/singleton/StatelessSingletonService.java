package singleton;

public class StatelessSingletonService {

    public int order(String user, int price) {
        System.out.printf("[%s] 주문 금액 : %d\n", user, price);
        return price;
    }
}
