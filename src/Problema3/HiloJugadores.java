package Problema3;

class Jugadores{
    private int jugador = 0;

    public Jugadores(int valorInicial) {
        this.jugador = valorInicial;
    }


    synchronized public int getNumJug(){
        return jugador;
    }

    synchronized   public int aumentaJugador(){
        this.jugador++;
        return jugador;
    }

    synchronized public int decrementaJugador(){
        this.jugador--;
        return  jugador;
    }
}

class hiloIncr implements Runnable{
    private final String id;
    private final Jugadores jugadores;

    hiloIncr (String id, Jugadores j){
        this.id = id;
        this.jugadores = j;
    }

    @Override
    public void run() {
        while (true){
            synchronized (this.jugadores){
                while (this.jugadores.getNumJug() > 9){
                    System.out.printf("!!!Hilo %s no se puede incrementar el número de Jugadores: %d\n", this.id, this.jugadores.getNumJug());
                    try{
                        this.jugadores.wait();
                    }catch (Exception ex){
                    }
                }

                this.jugadores.aumentaJugador();
                this.jugadores.notify();
                System.out.printf("Hilo %s incrementa, numero de jugadores: %d\n", this.id, this.jugadores.getNumJug());
            }
        }
    }
}
class hiloDecr implements Runnable{
    private final String id;
    private final Jugadores jugadores;

    hiloDecr(String id, Jugadores j) {
        this.id = id;
        this.jugadores = j ;
    }

    @Override
    public void run() {
        while (true){
            synchronized (this.jugadores){
                while (this.jugadores.getNumJug()<0){
                    System.out.printf("!!!Hilo %s no se puede quitar un jugador, numero de jugadores: %d\n", this.id, this.jugadores.getNumJug());
                    try {
                        this.jugadores.wait();
                    }catch (Exception ex){}
                }
                this.jugadores.decrementaJugador();
                this.jugadores.notify();
                System.out.printf("Hilo %s decrementa, numero de jugadores: %d\n", this.id, this.jugadores.getNumJug());
            }
        }
    }
}

public class HiloJugadores {
    public static final int NUM_HILOS_INC = 5;
    public static final int NUM_HILOS_DEC = 5;

    public static void main(String[] args) {
        Jugadores j = new Jugadores(0);
        Thread[] anadirJugador = new Thread[NUM_HILOS_INC];
        for (int i = 0; i < NUM_HILOS_INC; i++){
            Thread th = new Thread(new hiloIncr("INC"+i,j));
            anadirJugador[i] = th;
        }
        Thread[] quitarJugador = new Thread[NUM_HILOS_DEC];
        for (int i = 0; i < NUM_HILOS_DEC; i++){
            Thread th = new Thread(new hiloDecr("DEC"+i,j));
            quitarJugador[i] = th;
        }
        for (int i = 0; i < anadirJugador.length; i++){
            anadirJugador[i].start();
        }
        for (int i = 0; i < quitarJugador.length; i++){
            quitarJugador[i].start();
        }
        for (int i = 0; i < anadirJugador.length; i++){
            try {
                anadirJugador[i].join();
            } catch (Exception ex){}
        }
        for (int i = 0; i < quitarJugador.length; i++){
            try {
                quitarJugador[i].join();
            } catch (Exception ex){}

        }
    }
}
