package thread.demo;

public class WaitNotify {

  public static void main(String[] args) {
    final Object atm = new Object();

    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (atm) {
          try {
            System.out.println("It's thread1's turn to use ATM");
            Thread.sleep(1000);
            System.out.println("Thread1 had pickup a call, will give chance to next one");

            atm.wait(100);
            System.out.println("Thread1 gets the chance to go on");

            System.out.println("Thread1 done");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });

    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (atm) {
          try {
            System.out.println("It's thread2's turn to use ATM");
            Thread.sleep(2000);
            System.out.println("Thread2 done");
            atm.notify();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });

    thread1.start();
    thread2.start();
  }
}
