package sockets.main;

/**
 * Created by Fabián on 3/28/2016.
 */
import java.io.IOException;
import sockets.servidor.ServerTest;


public class MainServer {
    public static void main(String[] args) throws IOException{
        ServerTest serv = new ServerTest(); //Se crea el servidor

        System.out.println("Iniciando servidor\n");
        serv.startServer(); //Se inicia el servidor
    }
}
