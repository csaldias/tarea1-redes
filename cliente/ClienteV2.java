package sockets.cliente;

import java.io.DataOutputStream;
import java.io.IOException;
import sockets.conexion.Conexion;
import java.util.Scanner;

public class ClienteV2 extends Conexion
{
    public ClienteV2() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient() //Método para iniciar el cliente
    {
        try {
            while (true) {
                System.out.println("Ingrese Opción:");
                System.out.println("1-Enviar Mensaje.");
                System.out.println("2-Pointless.");
                System.out.println("3-Cerrar Conexión.");
                //Flujo de datos hacia el servidor
                //System.out.println("Ingrese Mensaje");
                Scanner scan = new Scanner(System.in);
                String text = scan.nextLine();
                if(text.equals("1")/**1**/) {
                    salidaServidor = new DataOutputStream(cs.getOutputStream());

                    //Se enviarán dos mensajes
                    for (int i = 0; i < 2; i++) {
                        System.out.println("Ingrese Mensaje");
                        Scanner scan1 = new Scanner(System.in);
                        String text1 = scan.nextLine();
                        //Se escribe en el servidor usando su flujo de datos
                        salidaServidor.writeChars(text1 + "\n");
                    }
                }
                else if(text.equals("3")/**3**/) {
                    cs.close();//Fin de la conexión
                    break;
                }
                else{
                    System.out.println("¯\\_(ツ)_/¯");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}