package thread.demo;

public class SyncDemo {
  public static void main(String[] args) {
    SyncDemo syncDemo = new SyncDemo();

    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
         syncDemo.syncMethod();
      }
    }, "Thread1");

    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        syncDemo.syncMethod();
      }
    },"Thread2");

    thread1.start();
    thread2.start();
  }

  public static synchronized void syncMethod(){
    try {
      System.out.println("It's " + Thread.currentThread().getName() + "turn to use ATM");
      Thread.sleep(2000);
      System.out.println("Thread1 done");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void demo3(){
    synchronized (SyncDemo.class){
      System.out.println("");
    }
  }
}
