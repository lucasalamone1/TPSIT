package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {
	
	static Scanner scanner;
	
	public static void main(String[] args) throws Exception {
		try {
			String severAddress="127.0.0.1";  // localhost
			int severPort=8698;
						
			String clientCommand = "";
			String serverMessage = "";
			
			printMenu();	
			
			// Viene creata una connessione al server
			System.out.print("Client: Tentativo di connessione server=" + severAddress + ":" + severPort + " ... ");
			Socket socket = new Socket(severAddress, severPort); //

			System.out.println("Connected");
			// Creo inStream/outStream per leggere e scrivere dati
			DataInputStream inStream   = new DataInputStream(socket.getInputStream());
			DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());

			scanner = new Scanner(System.in);
			
			while (!clientCommand.equals("end")) {

				//Comandi inviati dall'utente
				clientCommand = prompt();
			    	System.out.println("Client: invio il comando: " + clientCommand);
				outStream.writeUTF(clientCommand);   
				outStream.flush();
				
				// Vengono letti i dati dal flusso di input del socket
				serverMessage = inStream.readUTF();      
				System.out.println("Client: ricevuto il messaggio: " + serverMessage);
		        
			}
			System.out.println("Client: disconnecting");
			// Chiusura delle risorse usate
			outStream.close();
			outStream.close();
			socket.close();
			scanner.close();
			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Exit");
		}
	}
	
	//Menu che mostra i comandi disponibili
	public static void printMenu() {
		System.out.println("Help: comandi disponibili");
		System.out.println("  help");
		System.out.println("  info");
		System.out.println("  prenota <numero> <nome>");
		System.out.println("  end");

	}
	
	//Funzione per far inserire un comando dall'utente
	public static String prompt() {
		scanner = new Scanner(System.in);
		System.out.print("inserisci comando> ");
		String cmd = scanner.nextLine();
		return cmd;
	}
}
