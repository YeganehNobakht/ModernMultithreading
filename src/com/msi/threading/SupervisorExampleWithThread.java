package com.msi.threading;

public class SupervisorExampleWithThread {
    public static void main(String[] args) {
        ParallelWorker1 parallelWorker1 = new ParallelWorker1();
        ParallerWorker2 parallerWorker2 = new ParallerWorker2();

        parallelWorker1.start();
        parallerWorker2.start();
    }
}

class ParallelWorker1 extends Thread{
    @Override
    public void run() {
        for (int i=0 ; i<10 ; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Worker"+i+"  is executing task : " + i);
        }
    }
}
class ParallerWorker2 extends Thread{
    @Override
    public void run() {
        for (int i=0 ; i<10 ; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Worker"+i+" is executing task : " + i);
        }
    }
}
