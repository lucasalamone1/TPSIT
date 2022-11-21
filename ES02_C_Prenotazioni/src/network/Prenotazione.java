package network;


public class Prenotazione {
   
    private String[] reservations;

    //Costruttore
    Prenotazione(int reservationsSize ) {
    	reservations=new String[reservationsSize];
    	for (int i=0 ; i<reservationsSize ; i++ ) {
    		reservations[i]="Posto disponibile";
    	}
    }
    
     
    public synchronized String getReservations() {
    	String res="";
    	for (int i=0 ; i<reservations.length ; i++ ) {
    		res= res + "\n" +  i + " " + reservations[i];
    	}
        return res;
    }
    
    public synchronized String setReservation( int seatNum, String name ) {
    	String result = "Prenotazione perr " + name + " Rifiutata";
    	if ( reservations[seatNum].equals("Posto disponibile")) {
    		 reservations[seatNum]=name;
    		 result="Prenotazione per " + name + " Accettata ";
    	} 
        return result;
    }
 
    
}
