package com.msi.threading;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SumOfNumbersUsingLatch {

    public static int[] array = IntStream.rangeClosed(0,5000).toArray();
    final static int total = IntStream.rangeClosed(0,5000).sum();
    final static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable callable1 = () ->{
            int sum = 0;
            for (int i=0; i<array.length/2 ; i++){
                sum += array[i];;
            }
            countDownLatch.countDown();
            return sum;
        };
        Callable callable2 = () ->{
            int sum = 0;
            for (int i=array.length/2; i<array.length ; i++){
                sum += array[i];;
            }
            countDownLatch.countDown();
            return sum;
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        Future sum1 = service.submit(callable1);
        Future sum2 = service.submit(callable2);

        System.out.println("CountDownLatch count before calling the await: " + countDownLatch.getCount());
        countDownLatch.await();
        System.out.println("CountDownLatchCount after await: " + countDownLatch.getCount());

        Integer sum = (Integer)sum1.get() + (Integer) sum2.get();

        System.out.println("Sum from the thread is: " + sum);
        System.out.println("Correct sum is: " + total);

    }
}
