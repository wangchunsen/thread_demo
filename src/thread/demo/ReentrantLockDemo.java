package thread.demo;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable {
  public static ReentrantLock lock1 = new ReentrantLock();
  public static ReentrantLock lock2 = new ReentrantLock();
  int lock;

  public ReentrantLockDemo(int lock) {
    this.lock = lock;
  }

//  public void aaa() {
//    lock1.tryLock();
//    try {
//      //.....
//    } finally {
//      lock1.unlock();
//    }
//
//  }

  @Override
  public void run() {
    try {
      if (lock == 1) {
        lock1.lockInterruptibly();  // 以可以响应中断的方式加锁
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        lock2.lockInterruptibly();
      } else {
        lock2.lockInterruptibly();  // 以可以响应中断的方式加锁
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
        }
        lock1.lockInterruptibly();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (lock1.isHeldByCurrentThread()) lock1.unlock();  // 注意判断方式
      if (lock2.isHeldByCurrentThread()) lock2.unlock();
      System.err.println(Thread.currentThread().getId() + "退出！");
    }
  }

  public static void main(String[] args) throws InterruptedException {
    ReentrantLockDemo deadLock1 = new ReentrantLockDemo(1);
    ReentrantLockDemo deadLock2 = new ReentrantLockDemo(2);
    Thread t1 = new Thread(deadLock1);
    Thread t2 = new Thread(deadLock2);
    t1.start();
    t2.start();
    Thread.sleep(1000);
    t2.interrupt(); // ③
  }

}
