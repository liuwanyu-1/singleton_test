package lwy.study.singleton;

public class Singleton6 {
    private Singleton6() {}

    private static class SingletonHolder {
        private static final Singleton6 INSTANCE = new Singleton6();
    }

    public static Singleton6 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
