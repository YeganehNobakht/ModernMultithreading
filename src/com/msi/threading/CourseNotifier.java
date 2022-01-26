package com.msi.threading;

public class CourseNotifier {
    public static void main(String[] args) {
        final Course course = new Course("Java multithreading programming");

        new Thread(()->{
            synchronized (course){
                System.out.println(Thread.currentThread().getName()
                        + " is wating for the course: " + course.getTitle());
                try {
                    course.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                course.notify();
                System.out.println(Thread.currentThread().getName() + " the course: "
                        + course.getTitle() + "is now completed");
            }
        },"StudentA").start();

        new Thread(()->{
            synchronized (course){
                System.out.println(Thread.currentThread().getName()
                        + " is wating for the course: " + course.getTitle());
                try {
                    course.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                course.notify();
                System.out.println(Thread.currentThread().getName() + "the course: "
                        + course.getTitle() + "is now completed");

            }
        },"StudentB").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "the course: "
                    + course.getTitle() + " is now completed");
            synchronized (course) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                course.notifyAll();
                course.notify();
            }

        },"StudentC").start();
    }
}


class Course{
    private  String title;
    private boolean completed;

    public Course(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Course setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Course setCompleted(boolean completed) {
        this.completed = completed;
        return this;
    }
}
