package modele;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCoteServeur extends Client{

	private Socket socketClient;
	private BufferedReader in;
	private PrintWriter out;
	//private ThreadE thread;

	
	public ClientCoteServeur(Socket socket,BufferedReader in, PrintWriter out) throws IOException {
		super("a");
		this.socketClient = socket;
		this.out = out;
		this.in = in;
		//this.thread = new ThreadE(this.socketClient,this.in,this.out);
	}
	
	public void setNomClientCo(String nom) {
		this.setNom(nom);
	}
	
	public String getNomClientCo() {
		return this.getNom();
	}


	public BufferedReader getIn() {
		return this.in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return this.out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public String toString() {
		return this.getNomClientCo() + "  " + this.socketClient;
	}
	
	
	

}
