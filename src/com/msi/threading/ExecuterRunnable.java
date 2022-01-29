package com.msi.threading;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class ExecuterRunnable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Runnable runnable = () -> {
            try {
                TimeUnit.MICROSECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "Finished executing at: " + LocalDateTime.now());

        };

        ExecutorService service = Executors.newFixedThreadPool(10);
        System.out.println("First Example - executing task with execute() method");
        service.execute(runnable);

        System.out.println("Second example executing task with submit() method");
        Future<String> result = service.submit(runnable, "Completed");

        while (!result.isDone()){
            System.out.println("The method return value: " + result.get());
        }

        service.shutdown();
    }
}
