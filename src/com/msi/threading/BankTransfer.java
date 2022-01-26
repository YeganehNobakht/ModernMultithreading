package com.msi.threading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankTransfer {
    private double balance;
    private int id;
    private String accountName;
    final Lock lock = new ReentrantLock();

    public BankTransfer(double balance, int id, String accountName) {
        this.balance = balance;
        this.id = id;
        this.accountName = accountName;
    }

    public boolean withdraw(double amount) throws InterruptedException {
        if (this.lock.tryLock()) {
            Thread.sleep(100);
            balance -= amount;
            this.lock.unlock();
            return true;
        }
        return false;
    }

    public boolean deposit(double amount) throws InterruptedException {
        if (this.lock.tryLock()) {
            Thread.sleep(100);
            balance += amount;
            this.lock.unlock();
            return true;
        }
        return false;
    }

    public boolean transfer(BankTransfer to, double amount) throws InterruptedException {
        if (withdraw(amount)) {
            System.out.println("Withdrawing amount: " + amount + " from " + this.accountName);
            if (to.deposit(amount)) {
                System.out.println("Depositing amount: " + amount + "to: " + to.accountName);
                return true;
            } else {
                System.out.println("Failed to acquire both locks: refunding " + amount + "to: " + this.accountName);
                while (!deposit(amount))
                    continue;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        BankTransfer studentBankTransfer = new BankTransfer(50000, 1, "studentA");
        BankTransfer universityBankTransfer = new BankTransfer(100000, 1, "university");

        System.out.println("Starting balance of account are: University: " + universityBankTransfer.balance
                + "student: " + studentBankTransfer.balance);

        ExecutorService service = Executors.newFixedThreadPool(10);

        Thread t = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() +
                    "says :: Executing Transfer");

            try {
                while (!studentBankTransfer.transfer(universityBankTransfer, 1000)) {
                    Thread.sleep(100);
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() +
                    "says transfer is successful");
        });

        for (int i = 0; i < 20; i++) {
            service.submit(t);
        }
        service.shutdown();
        try {

            while (!service.awaitTermination(24L, TimeUnit.HOURS)) {
                System.out.println("Not Yet, still waiting for termination");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending balance of student account: " + studentBankTransfer.balance +
                "university account: " + universityBankTransfer.balance);
    }
}
