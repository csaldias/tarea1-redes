package sockets.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class ServidorV3 //extends Conexion Se hereda de conexión para hacer uso de los sockets y demás
{
    private final int PUERTO = 1234; //Puerto para la conexión

    public void startServer()//Método para iniciar el servidor
    {
        try {
            ServerSocket ss = new ServerSocket(PUERTO);
            int id=0;
            System.out.println("Esperando..."); //Esperando conexión
            while (true) {
                Socket cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente
                ClientServiceThread cliThread = new ClientServiceThread(cs, id++);
                cliThread.start();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}


class ClientServiceThread extends Thread {
    Socket cs;
    int clientID = -1;
    public String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    public DataOutputStream salidaServidor, salidaCliente; //Flujo de datos de salida

    ClientServiceThread(Socket s, int i) {
        cs = s;
        clientID = i;
    }

    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    public void run() {
        try {
            System.out.println("Cliente en línea");
            //Se obtiene el flujo de salida del cliente para enviarle mensajes
            PrintWriter salidaCliente = new PrintWriter(cs.getOutputStream(), true);

            //Se obtiene el flujo entrante desde el cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream(),"UTF-8"));
            ArrayList<String> msgList = new ArrayList<String>();

            while ((mensajeServidor = entrada.readLine()) != null) //Mientras haya mensajes desde el cliente
            {
                if (mensajeServidor.isEmpty()){
                    salidaCliente.println("HTTP/1.1 200 OK\n" +
                            "Server: Java/1.0.0 (MacOS)\n" +
                            "Content-Type: text/html\n" +
                            "Connection: close\n\n" +
                            "<html>\n" +
                            "<body>\n" +
                            "<h1>Hello, World!</h1>\n" +
                            "</body>\n" +
                            "</html>\n");
                    entrada.close();
                    break;
                }
                msgList.add(mensajeServidor);
            }

            System.out.println("Cliente Desconectado.");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}