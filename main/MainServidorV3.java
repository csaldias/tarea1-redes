package main;

import java.io.IOException;
import servidor.ServidorV3;


//Clase principal que hará uso del servidor
public class MainServidorV3
{
    public static void main(String[] args) throws IOException
    {
        ServidorV3 serv = new ServidorV3(); //Se crea el servidor

        System.out.println("Iniciando servidor\n");
        serv.startServer(); //Se inicia el servidor
    }
}
