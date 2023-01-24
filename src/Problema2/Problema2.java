package Problema2;
/*

                                        PROBLEMA DE INTERBLOQUEO

               Suponemos que tenemos dos cestos llenos de pelotas de baloncesto.

               Para realizar el intercambio de pelotas entre ambos necesitamos exclusividad de acceso a ambos.

               Se pide realizar una clase llamada IntercambioBalones donde consigamos un acceso exclusivo y ordenado
               (siempre igual) a los dos cestos de pelotas e indicar que métodos de las otras clases necesitarían ser
               synchronized.

               Para la realización de esta clase disponemos de una clase llamada Cesto con sus métodos:
               (Constructor, getNumBalones, guardarBalon, sacarBalon, getNumCesto).

               Y otra clase Hilo con sus métodos:
               (Constructor, run, getNomHilo).

               Y la clase principal IntercamvioBalonesSinBloqueo donde creamos los hilos.

               (4ptos)

 */
class Cesto{
    Object cesto1 = new Object();
    Object cesto2 = new Object();
    int numCesto;
    int numBalones = 0;
    public Cesto(int numCesto) {
        this.numCesto = numCesto;
    }
    public void sacarBalon(){
            numBalones--;
    }
    public void guardarBalon(){
        this.numBalones ++;
    }
    public int getNumBalones(){
        return this.numBalones;
    }
    public int getNumCesto() {
        return numCesto;
    }

    public synchronized void accesoACestos1y2(){
        try{
            System.out.println("Soy el hilo " + Thread.currentThread().getName() + " y ahora mismo estoy usando le cesto 1");
            synchronized (cesto1){
                Thread.sleep(2000);
                synchronized (cesto2){
                    System.out.println("Soy el hilo " + Thread.currentThread().getName() + " y ahora mismo acabo de terminar.");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void accesoACestor2y1(){
        try{
            System.out.println("Soy el hilo " + Thread.currentThread().getName() + " y ahora mismo estoy usando el cesto 2");
            synchronized (cesto2){
                Thread.sleep(2000);
                synchronized (cesto1){
                    Thread.sleep(2000);
                    System.out.println("Soy el hilo " + Thread.currentThread().getName() + " y ahora mismo acabo de terminar");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
class Hilo extends Thread{
    int idHilo;
    Cesto cesto;
    public Hilo(int idHilo, Cesto cesto) {
        this.idHilo = idHilo;
        this.cesto = cesto;
    }
    @Override
    public void run() {
        if (idHilo == 1)
            cesto.accesoACestos1y2();
        else
            cesto.accesoACestor2y1();
    }
}
public class Problema2 {
    public static void main(String[] args) {


        Cesto cesto = new Cesto(1);

        Thread hilo1 = new Thread(new Hilo(1, cesto));
        Thread hilo2 = new Thread(new Hilo(2, cesto));

        hilo1.setName("Hilo1");
        hilo2.setName("Hilo2");

        hilo1.start();
        hilo2.start();

        try{
            hilo1.join();
            hilo2.join();
            System.out.println("Este mensaje se muestra porque se ha ejecutado todo correctamente.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
