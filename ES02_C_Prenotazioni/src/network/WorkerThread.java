package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class WorkerThread extends Thread{
	Socket sock;
	int clientNo;
	Prenotazione reservations;
	

	WorkerThread( String threadName , Socket inSocket, int ClientNo ,  Prenotazione res) {
		super(threadName);
		sock = inSocket;
		clientNo = ClientNo;
		reservations=res;
	}

	public boolean isNumeric( String stringa_numerica) {
	  boolean result=true;
	  
	  try {
	        int num = Integer.parseInt(stringa_numerica);
	      } catch (NumberFormatException e) {
	        result = false;
	      }
	  return result;
	}
	
	public String joinArray( String a[] , int from , int to ) {
		  String s="";
		  for (int i=from ; i<=to ; i++) {
			  s=s+ " " + a[i];
		  }
		  return s;
		}
	
	public void run() {
		try {

			InetSocketAddress clienAddr = (InetSocketAddress) sock.getRemoteSocketAddress();
	        System.out.println("ServerTread: " + this.getName() + " New connection from port=" + clienAddr.getPort() + " host=" + clienAddr.getHostName());
	        
			// Streams to read and write the data to socket streams
			DataInputStream inStream   = new DataInputStream(sock.getInputStream());
			DataOutputStream outStream = new DataOutputStream(sock.getOutputStream());

			String clientCommand = "";
			String serverResponse = "";

			while (!clientCommand.equals("end")) {

				clientCommand = inStream.readUTF();  // bloccante
				
				// elaboro il comando del client
				clientCommand.trim();
				String clientCommandArr[]=clientCommand.split("\\s+");
				System.out.println("ServerTread: " + this.getName() + " Comando ricevuto: " + clientCommand );
				int n;
				switch (clientCommandArr[0]) {
					                         
				  case "info" :  serverResponse=reservations.getReservations();
                                 break;
                            
				  case "prenota": 
					             //   0      1    2     3
					             // reserve 15  Luca Liberti
							     System.out.println("ServerTread: " + this.getName() +" (prenota) num="+  clientCommandArr[1] + " nome=" +  clientCommandArr[2] );
					             if (isNumeric(clientCommandArr[1]) == true) {
					            	 int num=Integer.parseInt(clientCommandArr[1]);
					            	 //String name=clientCommandArr[2];
					            	 String name=joinArray(clientCommandArr , 2 , clientCommandArr.length-1 );
								     serverResponse=reservations.setReservation(num, name);
					             } else {
					            	 serverResponse="comand reserve: syntax error";
					             }
                                 break;
		            
				  case "help" :  serverResponse="Help: comandi disponibili --> help, info, prenota <num> <nome>, end";
				                 break;
				                 
				  case "end" :  serverResponse="Arrivederci";
	                            break;
		                		
				  default:      serverResponse="commando " + clientCommand + " non trovato";
		                        break;
				}
				
				System.out.println("ServerTread: " + this.getName() + " invio risposta " + serverResponse );
				outStream.writeUTF(serverResponse);
				outStream.flush();
			}
			
			serverResponse="Bye";
			System.out.println("Server.Thread " + clientNo + " Invio messaggio " + clientCommand );
			outStream.writeUTF(serverResponse);
			outStream.flush();
			inStream.close();
			outStream.close();
			sock.close();

		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			System.out.println("Client -" + clientNo + " exit!! ");
		}
	}

	

}
