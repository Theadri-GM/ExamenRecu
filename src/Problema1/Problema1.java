package Problema1;

import java.io.*;
import java.util.Scanner;

/*
                                            PROBLEMA 1

        Se pide implementar un programa en java, llamado MD5.java.

        Este permite obtener el valor del hash md5 para el texto introducido desde la entrada estándar.

        El programa leerá de la entrada estándar, y escribirá el resultado del comando md5su, para cada línea, hasta que
        se introduzca una linea vacía.
 */
public class Problema1 {
    public static void main(String[] args) throws IOException {
            String linea = new Scanner(System.in).nextLine();
            ProcessBuilder pb = new ProcessBuilder("echo", linea);
            ProcessBuilder pb2 = new ProcessBuilder("md5sum", "redireccion.txt");
            pb.redirectOutput(new File("redireccion.txt"));

            Process p;
            try {
                p = pb2.start();
                BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String linea2;
                while ((linea2 = bf.readLine()) != null) {
                    System.out.println(linea2);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

    }
}
