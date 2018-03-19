package kz.technodom.autotest;

import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) {
        int random = ThreadLocalRandom.current().nextInt(0, 10 + 1);
        System.out.println(random);
    }
}
