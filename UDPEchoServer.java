package udpechoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPEchoServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Porta di ascolto del server
        int port = 50000;
        
        //Socket UDP
        DatagramSocket dSocket;
        
        //Pacchetto UDP ricevuto
        DatagramPacket inPacket;
        
        //Pacchetto UDP inviato
        DatagramPacket outPacket;
        
        //Buffer per il contenuto del pacchetto ricevuto
        byte buffer[];
        
        //Oggetti String nei quali metteremo il messaggio ricevuto dal client
        //e il messaggio da inviare al client come risposta
        String messaggioIn;
        String messaggioOut;
        
        try {
            
            //Creazione del socket UDP
            dSocket= new DatagramSocket(port);
            while(true){
                System.out.println("Server in ascolto sulla porta "+port);
                
                //Ricezione richiesta del client
                //Creazione di un buffer sufficiente a contenere i dati 
                //ricevuti dal client
                buffer = new byte[256];
                //creazione del DatagramPacket di ricezione passandogli il 
                //buffer e la sua lunghezza
                inPacket = new DatagramPacket(buffer, buffer.length);
                
                //Ci mettiamo in attesa dal pacchetto del client.
                //Questa operazione è BLOCCANTE!
                dSocket.receive(inPacket);
                
                //Estraggo dal pacchetto ricevuto l'indirizzo del client
                InetAddress clientAddress = inPacket.getAddress();
                
                //Estraggo dal pacchetto ricevuto la porta del client
                int clientPort = inPacket.getPort();
                
                //Estraggo dal pacchetto ricevuto i dati trasformandoli in String
                messaggioIn = new String(inPacket.getData(),0,inPacket.getLength());
                
                System.out.println("Richiesta dal client -> <"+clientAddress
                +" Porta:"+clientPort+">"+messaggioIn);
                
                //Invio risposta al client
                //Creazione del DatagramPacket da inviare al client come risposta
                outPacket = new DatagramPacket(
                        messaggioIn.getBytes(),
                        messaggioIn.length(),
                        clientAddress,
                        clientPort
                );
                
                //invio della risposta
                dSocket.send(outPacket);
            }    
        } catch (SocketException ex) {
            Logger.getLogger(UDPEchoServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDPEchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
