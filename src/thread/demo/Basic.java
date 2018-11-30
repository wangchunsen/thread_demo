package thread.demo;

public class Basic {
  public static void main(String[] args){
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("This is running in a thread");
      }
    });
    thread.start();
  }
}
