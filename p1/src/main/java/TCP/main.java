/*
    UNIVERSIDAD GALILEO
    Ciencias de la Computación VIII
    Luis Florian - Sección AN
 */
package TCP;

import java.io.*;

public class main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        String op;              //Get input option
        String puertoDestino;   //Get destination port. 
        String ipDestino;       //Get destionatio IP Address
        String fileToSend;      //Transfering file name 
        int socketPort;         //Getting socket port to create socket
        
        System.out.println("\n    ************************* UNIVERSIDAD GALILEO **************************");
        System.out.println("    *  Ciencias de la Computación VIII                                     *");
        System.out.println("    *  Luis Florian                                                        *");
        System.out.println("    *  Sección AN                                                          *");
        System.out.println("    ************************************************************************");
        
        System.out.println("\n    Elija una opción de modalidad");
        System.out.println("       1. Cliente ");
        System.out.println("       2. Servidor ");
        System.out.println("       3. Exit");
        System.out.print("    Opción:   ");
        op = br.readLine();       
        
        switch (op){
            case "1" :  System.out.print("    Ingrese IP destino:  ");
                        ipDestino = br.readLine();
                        System.out.print("    Ingrese puerto destino: ");
                        puertoDestino = br.readLine();
                        System.out.print("    Ingrese nombre de documento a transferir: ");
                        fileToSend = br.readLine();
                        System.out.print("    Ingrese número de puerto para comunicación: ");
                        socketPort = Integer.parseInt(br.readLine());
                        client newClient = new client(puertoDestino, ipDestino, fileToSend, socketPort);
                        newClient.socketCreation();
                        newClient.threeWayHandShake();
                        break;
                        
            
            case "3" :  break;  
            
            default  :  System.out.println("    Opción incorrecta");
        }

    }
    
}
