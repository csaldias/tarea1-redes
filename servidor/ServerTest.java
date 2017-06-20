package sockets.servidor;

/**
 * Created by Fabián on 3/28/2016.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    private final int PUERTO = 1234; //Puerto para la conexión
    protected ServerSocket ss; //Socket del servidor
    protected Socket cs; //Socket del cliente
    protected DataOutputStream salidaServidor, salidaCliente; //Flujo de datos de salida
    protected String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor

    public void startServer() {
        try {
            ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234
            cs = new Socket(); //Socket para el cliente

            cs = ss.accept();

            //recibe flujo de salida del cliente.
            salidaCliente = new DataOutputStream(cs.getOutputStream());
            salidaCliente.writeUTF("Petición recibida y aceptada");

            BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));

            while((mensajeServidor = entrada.readLine()) != null) //Mientras haya mensajes desde el cliente
            {
                //Se muestra por pantalla el mensaje recibido
                System.out.println(mensajeServidor);
            }
            System.out.println("Fin de la conexión");

            ss.close();//Se finaliza la conexión con el cliente
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
