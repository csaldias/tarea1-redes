package sockets.cliente;

/**
 * Created by Fabián on 3/27/2016.
 */
import java.io.DataOutputStream;
import java.io.IOException;
import sockets.conexion.Conexion;
import java.util.Scanner;

public class Cliente extends Conexion
{
    public Cliente() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient() //Método para iniciar el cliente
    {
        try
        {
            //Flujo de datos hacia el servidor
            salidaServidor = new DataOutputStream(cs.getOutputStream());

            //Se enviarán dos mensajes
            for (int i = 0; i < 5; i++)
            {
                System.out.println("Ingrese Mensaje");
                Scanner scan= new Scanner(System.in);
                String text= scan.nextLine();
                //Se escribe en el servidor usando su flujo de datos
                salidaServidor.writeUTF( text + "\n");
            }

            cs.close();//Fin de la conexión

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}