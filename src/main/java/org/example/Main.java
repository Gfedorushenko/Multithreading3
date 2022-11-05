package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger atomicInt3 = new AtomicInteger(0);
    static AtomicInteger atomicInt4 = new AtomicInteger(0);
    static AtomicInteger atomicInt5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable logic1 = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (lettersInAscendingOrder(texts[i])) {
                    beautifulWord(texts[i]);
                }
            }
        };
        Runnable logic2 = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isPalindrome(texts[i])) {
                    beautifulWord(texts[i]);
                }
            }
        };
        Runnable logic3 = () -> {
            for (int i = 0; i < texts.length; i++) {
                if (isOneCar(texts[i])) {
                    beautifulWord(texts[i]);
                }
            }
        };

        Thread thread1 = new Thread(logic1);
        thread1.start();
        Thread thread2 = new Thread(logic2);
        thread2.start();
        Thread thread3 = new Thread(logic3);
        thread3.start();


        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + atomicInt3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + atomicInt4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + atomicInt5.get() + " шт");
    }

    public static void beautifulWord(String s) {
        if (s.length() == 3)
            atomicInt3.incrementAndGet();
        else if (s.length() == 4)
            atomicInt4.incrementAndGet();
        else if (s.length() == 5)
            atomicInt5.incrementAndGet();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < (n / 2); ++i) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean lettersInAscendingOrder(String s) {
        int n = s.length();
        if (s.charAt(0) != s.charAt(n - 1)) {
            for (int i = 0; i < n - 1; ++i) {
                if ((int) s.charAt(i) > (int) s.charAt(i + 1)) {
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public static boolean isOneCar(String s) {
        for (int i = 1; i < s.length(); ++i) {
            if ((int) s.charAt(0) != (int) s.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}