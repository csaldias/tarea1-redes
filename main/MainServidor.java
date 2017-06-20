package sockets.main;

/**
 * Created by Fabián on 3/27/2016.
 */
import java.io.IOException;
import sockets.servidor.Servidor;


//Clase principal que hará uso del servidor
public class MainServidor
{
    public static void main(String[] args) throws IOException
    {
        Servidor serv = new Servidor(); //Se crea el servidor

        System.out.println("Iniciando servidor\n");
        serv.startServer(); //Se inicia el servidor
    }
}