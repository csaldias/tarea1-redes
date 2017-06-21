package servidor;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;

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
            //Se obtiene el flujo de salida del cliente para enviarle mensajes
            PrintWriter salidaCliente = new PrintWriter(cs.getOutputStream(), true);

            //Se obtiene el flujo entrante desde el cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream(),"UTF-8"));

            //Leemos el header
            String[] header = entrada.readLine().split(" ");
            HashMap<String, String> header_data = new HashMap<>();
            String authorized_user = "username=root&passwd=rdc2017&action=Ingresar";
            Path access_log = Paths.get("log.txt");
            String ip = cs.getInetAddress().toString().replace("/", "");
            while ((mensajeServidor = entrada.readLine()) != null) //Mientras haya mensajes desde el cliente
            {
                String[] line = mensajeServidor.split(": ");
                if(!mensajeServidor.isEmpty()) header_data.put(line[0], line[1]);

                if (mensajeServidor.isEmpty()){
                    System.out.println("Received: "+ header[0] + " " + header[1]);

                    if (header[0].equals("GET")) {
                        switch (header[1]) {
                            case "/" :
                                salidaCliente.println("HTTP/1.1 200 OK\n" +
                                        "Server: Java/1.0.0 (MacOS)\n" +
                                        "Content-Type: text/html\n" +
                                        "Connection: close\n\n" +
                                        "<html>\n" +
                                        "<body>\n" +
                                        "<h1>Hello, World!</h1>\n" +
                                        "</body>\n" +
                                        "</html>\n");
                                break;
                            case "/home_old" :
                                salidaCliente.println("HTTP/1.1 301 Moved Permanently\n" +
                                        "Location: http://127.0.0.1:1234/\n" +
                                        "Server: Java/1.0.0 (MacOS)\n" +
                                        "Content-Type: text/html\n" +
                                        "Connection: close\n\n");
                                break;
                            case "/secret":
                                File check_auth = new File("user_authenticated.txt");
                                if (check_auth.exists()) {
                                    salidaCliente.println("HTTP/1.1 200 OK\n" +
                                            "Server: Java/1.0.0 (MacOS)\n" +
                                            "Content-Type: text/html\n" +
                                            "Connection: close\n\n" +
                                            "<html>\n" +
                                            "<body>\n" +
                                            "<h1>Autenticacion completada!</h1>\n" +
                                            "</body>\n" +
                                            "</html>\n");
                                } else {
                                    salidaCliente.println("HTTP/1.1 403 Forbidden\n" +
                                            "Server: Java/1.0.0 (MacOS)\n" +
                                            "Content-Type: text/html\n" +
                                            "Connection: close\n\n" +
                                            "<html>\n" +
                                            "<body>\n" +
                                            "<h1>Error!</h1>\n" +
                                            "<p>Usted no tiene permisos para ver esta pagina.</p>" +
                                            "</body>\n" +
                                            "</html>\n");
                                }
                                break;
                            case "/login" :
                                salidaCliente.println("HTTP/1.1 200 OK\n" +
                                        "Server: Java/1.0.0 (MacOS)\n" +
                                        "Content-Type: text/html\n" +
                                        "Connection: close\n\n" +
                                        "<html>\n" +
                                        "<body>\n" +
                                        "<form action=\"/secret\" method=\"POST\">\n" +
                                        "  Usuario:<br>\n" +
                                        "  <input type=\"text\" name=\"username\" >\n" +
                                        "  <br>\n" +
                                        "  Contraseña:<br>\n" +
                                        "  <input type=\"password\" name=\"passwd\" >\n" +
                                        "  <br><br>\n" +
                                        "  <input type=\"submit\" name=\"action\" value=\"Ingresar\">\n" +
                                        "</form>\n" +
                                        "</body>\n" +
                                        "</html>\n");
                        }

                    } else if (header[0].equals("POST")) {
                        System.out.println("Reading POST Data...");
                        int size;
                        String post_msg = "";
                        while ((size = entrada.read()) != -1) {
                            char c = (char)size;
                            post_msg += String.valueOf(c);
                            byte[] utf8Bytes = post_msg.getBytes("UTF-8");
                            if (utf8Bytes.length >= Integer.parseInt(header_data.get("Content-Length").toString())) break;
                        }
                        System.out.println("Read!");
                        if (post_msg.equals(authorized_user)) {
                            System.out.println("User is valid.");
                            File auth_mark = new File("user_authenticated.txt");
                            auth_mark.createNewFile();
                        }

                        switch (header[1]) {
                            case "/secret":
                                File check_auth = new File("user_authenticated.txt");
                                if (check_auth.exists()) {
                                    salidaCliente.println("HTTP/1.1 200 OK\n" +
                                            "Server: Java/1.0.0 (MacOS)\n" +
                                            "Content-Type: text/html\n" +
                                            "Connection: close\n\n" +
                                            "<html>\n" +
                                            "<body>\n" +
                                            "<h1>Autenticacion completada!</h1>\n" +
                                            "</body>\n" +
                                            "</html>\n");
                                } else {
                                    salidaCliente.println("HTTP/1.1 403 Forbidden\n" +
                                            "Server: Java/1.0.0 (MacOS)\n" +
                                            "Content-Type: text/html\n" +
                                            "Connection: close\n\n" +
                                            "<html>\n" +
                                            "<body>\n" +
                                            "<h1>Error!</h1>\n" +
                                            "<p>Usted no tiene permisos para ver esta pagina.</p>" +
                                            "</body>\n" +
                                            "</html>\n");
                                }
                                break;
                        }
                    }

                    entrada.close();
                    break;
                }

            }

            List<String> lines = Arrays.asList(ip+" "+header[0]+" "+header[1]+" "+getServerTime());
            Files.write(access_log, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}