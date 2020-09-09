/*
    UNIVERSIDAD GALILEO
    Ciencias de la Computación VIII
    Luis Florian - Sección AN
 */
package TCP;

import TCP.utils.FormatLogger;
import java.util.logging.*;
import java.lang.StringBuffer;
import java.io.*;
import java.net.*; 
import java.nio.ByteBuffer;


public class client {
    private static final Logger LOGGER = Logger.getLogger(client.class.getName());
    String puertoDestino;
    String puertoOrigen = "4C46";   // LF  
    String headerHex;               // The whole header
    String ipDestino;
    String ipOrigen;
    String fileToSend;  
    int socketPort;     
    DatagramSocket clientSocket;
    InetAddress IPAddress;
    byte[] sendData, receiveData;
    
    public client(String puertoDestino, String ipDestino, String fileToSend, int socketPort){
        this.puertoDestino = puertoDestino;
        this.ipDestino = ipDestino;
        this.fileToSend = fileToSend;           
        this.socketPort = socketPort;
    }
    
    void socketCreation() throws Exception {
        clientSocket = new DatagramSocket();
        
        IPAddress = InetAddress.getByName(ipDestino);
        
        sendData = new byte[1484];
        receiveData = new byte[1484];    
    } 
    
    void threeWayHandShake() throws Exception{    
        int SYNC = 0x00000001;
        /*System.out.println("    ************************************************************************");
        LOGGER.log(Level.INFO, "Establishing Connection to - Port: " + socketPort + " IP Address: ", IPAddress.getHostAddress());
        LOGGER.log(Level.INFO, "Three-Way-Handshake started - Local Port:{0}", clientSocket.getLocalPort()); */
        
       // sendData[0] = (byte) puertoOrigen;                                  //Puerto Origen 
      //  sendData[1] = (byte) corrimiento(puertoOrigen, 8, "derecha");       //Puerto Destino 
       
        //Adding puerto Origen
        headerHex = puertoOrigen;          
               
        //Adding puerto destino
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(0).array()).substring(0, 4);        
        
        //Adding sequence number = 1
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(1).array());        
       
        //Adding ack = 0
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(0).array());        
        
        //Offset = 0
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(0).array()).substring(0, 1);        
        
        //Reserved = 0
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(0).array()).substring(0, 2);        
        
        //TCP Flags = 0
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(0).array()).substring(0, 1);        
        
        //Windows = 1
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(1).array()).substring(4, 8);
                
        //Checksum = 0
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(0).array());
                
        //Options + relleno = 0
        headerHex += encodeHexString(ByteBuffer.allocate(4).putInt(0).array());
                
        //Datos = 0
        headerHex += encodeHexString(ByteBuffer.allocate(32).putInt(0).array());        
               
        //writing to sendData
        sendData = decodeHexString(headerHex);
        
        for (int i=0; i< sendData.length; i++){
            System.out.printf("%02x",sendData[i]);
        }
        
        System.out.println("\n");
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, socketPort);
        clientSocket.send(sendPacket);
      
            
       //System.out.printf("\n%02x\n",sendData[0]);
       //System.out.printf("%02x",sendData[1]);     
    }
    
    
    int corrimiento(int num, int posiciones, String sentido){
        int r;
        if (sentido.equals("derecha")) r = num >> posiciones;
        else r = num << posiciones;        
        return r;
    }
    
    //Devuelve String
    public String encodeHexString(byte[] byteArray) {
        StringBuffer hexStringBuffer = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
           // System.out.println("i: " + i);
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }   
    
    // Devuelve bytes
    public byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
                    "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }

    public String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }
    
    private int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException("Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    
        
}   
  

    

