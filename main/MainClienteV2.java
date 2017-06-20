package sockets.main;
import java.io.IOException;
import sockets.cliente.ClienteV2;


//Clase principal que hará uso del cliente
public class MainClienteV2
{
    public static void main(String[] args) throws IOException
    {
        ClienteV2 cli = new ClienteV2(); //Se crea el cliente
        System.out.println("Iniciando cliente\n");
        cli.startClient(); //Se inicia el cliente
    }
}
