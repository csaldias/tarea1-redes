package sockets.main;

/**
 * Created by Fabián on 3/28/2016.
 */
import java.io.IOException;
import sockets.cliente.ClientTest;


//Clase principal que hará uso del cliente
public class MainClient
{
    public static void main(String[] args) throws IOException
    {
        ClientTest cli = new ClientTest(); //Se crea el cliente

        System.out.println("Iniciando cliente\n");
        cli.startClient(); //Se inicia el cliente
    }
}