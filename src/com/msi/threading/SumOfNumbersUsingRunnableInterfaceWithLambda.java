package com.msi.threading;

import java.util.stream.IntStream;

public class SumOfNumbersUsingRunnableInterfaceWithLambda {
    public static int[] numbers = IntStream.rangeClosed(0,5000).toArray();
    public static int sum = 0;
    public static int total = IntStream.rangeClosed(0,5000).sum();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(()-> {
            for (int i=0; i<numbers.length/2;i++){
                add(numbers[i]);
            }
        });

        Thread thread2 = new Thread(()-> {
            for (int i=numbers.length/2; i<numbers.length;i++){
                add(numbers[i]);
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Sum of 5000 integer in parallel is : "+ sum);
        System.out.println("Sum of 5000 integer from intstream sum is : "+ total);
    }

    public synchronized static void add (int add){
        sum +=add;
    }
}
