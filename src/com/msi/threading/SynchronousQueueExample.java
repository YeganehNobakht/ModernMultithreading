package com.msi.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueExample {
    public static void main(String[] args) {
        final String[] names = {"Masi", "Nari", "Sepehr", "Mike", "Henry", "Jenny"};
        final SynchronousQueue<String> queue = new SynchronousQueue<>();

        Runnable producer = () -> {
            for (String name : names) {
                try {
                    queue.put(name);
                    System.out.println("Inserted: " + name + " in the queue");
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable consumer = () -> {
            while (true) {
                try {
                    System.out.println("Retrieval: " + queue.take() + " from the queue");
                    TimeUnit.MICROSECONDS.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(producer);
        service.submit(consumer);
        service.shutdown();
    }
}
