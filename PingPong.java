import java.lang.Thread;

public class PingPong extends Thread{
    private String palavra;

    public PingPong(String palavra) { this.palavra = palavra; }
    public void run() { //implementa o método run
        for (int i = 0; i < 10; i++) {
            System.out.print(palavra + " ");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando Threads");

        var ping = new PingPong("ping");
        var pong = new PingPong("pong");

        ping.start();
        pong.start();

        ping.join();
        pong.join();
        
        System.out.println("\nFinalizando Threads");
    }
}
