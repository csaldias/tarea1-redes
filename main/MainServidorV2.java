package sockets.main;

/**
 * Created by Fabián on 3/27/2016.
 */
import java.io.IOException;
import sockets.servidor.ServidorV2;


//Clase principal que hará uso del servidor
public class MainServidorV2
{
    public static void main(String[] args) throws IOException
    {
        ServidorV2 serv = new ServidorV2(); //Se crea el servidor

        System.out.println("Iniciando servidor\n");
        serv.startServer(); //Se inicia el servidor
    }
}
