import java.lang.Thread;

public class PingPong extends Thread{
    private String palavra;

    public PingPong(String palavra) { this.palavra = palavra; }
    public void run() { //implementa o método run
        for (int i = 0; i < 1000; i++) {
            System.out.print(palavra + " ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        new PingPong("ping").start();
        new PingPong("pong").start();
    }
}
