package udpechoclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPEchoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Porta di ascolto del server
        int serverPort = 50000;
        
        DatagramPacket outPacket;
        DatagramPacket inPacket;
        
        DatagramSocket dSocket;
        
        byte buffer[];
       
        String messaggioIn;
        String messaggioOut;
        
        InetAddress serverAddress;
        try {
            serverAddress = InetAddress.getByName("172.20.10.9");
            //Creazione del socket del client. Non è necessario specificare
            //il numero della porta tra quelle libere.
            dSocket = new DatagramSocket();
            
            //Prepariamo il datagramPacket da inviare al server
            messaggioOut = "Buongiorno";
            outPacket = new DatagramPacket(
                    messaggioOut.getBytes(),
                    messaggioOut.length(),
                    serverAddress,
                    serverPort
            );
            
            dSocket.send(outPacket);
            
            //Ricezione risposta dal server
            buffer= new byte[256];
            
            inPacket = new DatagramPacket(buffer, buffer.length);
            
            //Attesa risposta
            dSocket.receive(inPacket);
            
            messaggioIn = new String(inPacket.getData(),0,inPacket.getLength());
            
            //Visualizzazione risposta dal server
            System.out.println("Risposta dal server -> "+messaggioIn);
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(UDPEchoClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(UDPEchoClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPEchoClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
