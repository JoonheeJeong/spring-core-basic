package singleton;

public class SingletonService {

    private static final SingletonService INSTANCE = new SingletonService();

    private SingletonService() {
    }

    public static SingletonService getInstance() { return INSTANCE; }

    public void logic() {
        System.out.println("싱글톤 서비스 로직 호출");
    }
}
