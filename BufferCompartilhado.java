import java.util.LinkedList;
import java.util.Queue;

public class BufferCompartilhado {
    private final Queue<Integer> buffer;
    private final int capacidade;

    public BufferCompartilhado(int capacidade) {
        this.buffer = new LinkedList<>();
        this.capacidade = capacidade;
    }

    public synchronized void put(int valor) throws InterruptedException {
        // espera enquanto o buffer estiver cheio
        while (buffer.size() == capacidade) {
            wait();
        }

        buffer.add(valor);
        System.out.println("Produzido: " + valor + " | Tamanho do buffer: " + buffer.size());

        // notifica todas as threads que estao esperando
        notifyAll();
    }

    public synchronized int get() throws InterruptedException {
        // espera enquanto o buffer estiver vazio
        while (buffer.isEmpty()) {
            wait();
        }

        int valor = buffer.remove();
        System.out.println("Consumido: " + valor + " | Tamanho do buffer: " + buffer.size());

        // notifica todas as threads que estao esperando
        notifyAll();

        return valor;
    }

    public static void main(String[] args) {
        final BufferCompartilhado buffer = new BufferCompartilhado(5);

        // thread produtora
        Thread produtor = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.put(i);
                    Thread.sleep((int) (Math.random() * 500));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // thread consumidora
        Thread consumidor = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.get();
                    Thread.sleep((int) (Math.random() * 1000));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // inicia as threads
        produtor.start();
        consumidor.start();

        // aguarda as threads terminarem
        try {
            produtor.join();
            consumidor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Execução concluída.");
    }
}
