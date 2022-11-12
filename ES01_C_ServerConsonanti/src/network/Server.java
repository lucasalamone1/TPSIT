/**
 * from network/..
 * javac network/TcpServer.java
 * java network.TcpServer 
 */
package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception {
		
		int severPort=8765;
		String clientMsg = "";
		
		try {			 
			// Creazione del socket sul server e ascolto sulla porta
			ServerSocket serverSocket = new ServerSocket(severPort);
			System.out.println("Server: in ascolto sulla porta " + severPort);

			// Attesa della connessione con il client
			Socket clientSocket = serverSocket.accept();
			
			// Create input and output streams to read/write data
			DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	
			
			int i=0, voc=0, cons=0, len=0;
			String flag="";
			
			//Scambio di dati tra client e server
			do {
				
				voc=0; 
				cons=0;
				//Lettura dato da stream di rete
				clientMsg = inStream.readUTF();
				System.out.println("Server: ricevuto messaggio " + clientMsg );
				
				//Calcoliamo consonanti e vocali
				for(i=0; i<clientMsg.length(); i++) 
				{
					if(clientMsg.charAt(i)=='a'||clientMsg.charAt(i)=='e'||clientMsg.charAt(i)=='i'||clientMsg.charAt(i)=='o'||clientMsg.charAt(i)=='u') {
						voc++;
					} else {
						cons++;
					}
					
				}
				if(cons==(voc/2)&&voc%2==0) {
					clientMsg = "quit";
					flag="quit";
					
					outStream.writeUTF("Interruzione. Le consonanti sono la metà delle vocali");
				}
				else
				{
					outStream.writeUTF("Echo from server : Vocali: "  + voc +" Consonanti: " + cons);
					outStream.flush();
				}
						

				
				//Invio dati su stream di rete
				
				outStream.flush();
				//System.out.println(" from server : Lunghezza: " + len);
				
			}while(!flag.equals("quit"));
			

			
			// Close resources
			serverSocket.close();
			clientSocket.close();
			inStream.close();
			outStream.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}