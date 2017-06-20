package sockets.cliente;

/**
 * Created by Fabián on 3/28/2016.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;


public class ClientTest {
    private final int PUERTO = 1234; //Puerto para la conexión
    private final String HOST = "localhost"; //Host para la conexión
    protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    protected Socket cs; //Socket del cliente
    protected DataOutputStream salidaServidor, salidaCliente; //Flujo de datos de salida

    public void startClient() {
        try {
            cs = new Socket(HOST, PUERTO); //Socket para el cliente en localhost en puerto 1234

            salidaServidor = new DataOutputStream(cs.getOutputStream());
            for (int i = 0; i < 2; i++)
            {
                System.out.println("Ingrese Mensaje");
                Scanner scan= new Scanner(System.in);
                String text= scan.nextLine();
                //Se escribe en el servidor usando su flujo de datos
                salidaServidor.writeUTF( text + "\n");
            }

            cs.close();//Fin de la conexión


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
