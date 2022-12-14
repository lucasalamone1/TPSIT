/**
 * javac network/TheadedServer.java; java network.TheadedServer 
 */
package network;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;


public class MultithreadedTcpServer {

	static final int MAX_CONN = 999;
	static final int SRV_PORT = 8698;

	public static void main(String[] args) throws Exception {
	   
		int count = 0;                  // conta il numero di clienti 
		String risultato;
		// Creazione del socket
		ServerSocket server = new ServerSocket(SRV_PORT);
	
		while(count<MAX_CONN) {
			count++;
			// Attendiamo le richieste di connessione dei client
			System.out.println("Server: in ascolto sulla porta " + SRV_PORT );
			Socket serverClientSocket = server.accept();
			
			DataInputStream inStream = new DataInputStream(serverClientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(serverClientSocket.getOutputStream());
			
			System.out.println("Sto servendo il cliente " + count);
			// Gestisce la comunicazione con il Client
			TcpServer sa = new TcpServer(serverClientSocket, count);
			
			System.out.println("Cliente numero: "+count);
			risultato=""+count;   //la funzione outStream.writeUTF() può mandare solo tipi String, quindi per mandare la variabile count al client la avvaloro a "risultato"
			outStream.writeUTF(risultato);
			outStream.flush();
			
			sa.setName("Questo-e-il-mio-ServerThread-Numero-" +count);  
		}
		
		server.close();
	}
}
