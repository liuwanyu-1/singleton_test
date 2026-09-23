package lwy.study.singleton;

public class Singleton2 {
    private static Singleton2 instance;
    private Singleton2() {}
    static {
        instance = new Singleton2();
    }
    public static Singleton2 getInstance() {
        return instance;
    }
}
