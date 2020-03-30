package modele;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadServeur implements Runnable{
	
    private BufferedReader buffClient1;
    private PrintWriter printClient2;

    private boolean oui;
    
    
	public ThreadServeur(BufferedReader buffClient1, PrintWriter printClient2) throws IOException {
		this.buffClient1 = buffClient1;
		this.printClient2 = printClient2;
		this.oui = true;
		new Thread(this).start();
	}
	
	 @Override
	    public void run() {
			System.out.println("Thread Serveur lancé");
			String recu1 = "";
			while(!recu1.equals("!EXIT") && oui) {
				try {
					
					recu1 = this.buffClient1.readLine();
					
					if(recu1 != null) {
						this.printClient2.println(recu1);
						this.printClient2.flush();
					}

				} catch (IOException ex) {
				    Logger.getLogger(ThreadServeur.class.getName()).log(Level.SEVERE, null, ex);
				    this.arret();
				}
			}
	 }
	 
	 public void arret() {
		 this.oui = false;
	 }
	 
	 
}