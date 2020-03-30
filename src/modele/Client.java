package modele;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javafx.scene.control.TextArea;

public class Client {
	private String IP;
	private int port;
	private Socket s;
	private String nom;
	/**
	 * @return the thread
	 */
	public ThreadClient getThread() {
		return thread;
	}

	/**
	 * @param thread the thread to set
	 */
	public void setThread(ThreadClient thread) {
		this.thread = thread;
	}

	private ThreadClient thread;

	public Client(String IP, int port, String nom) throws UnknownHostException, IOException {
		this.nom = nom;
		try {
			this.s = new Socket(IP, port);
			System.out.println("CLIENT crée");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Client(String nom) {
		this.nom = nom;
	}

	public Socket getSocket() {
		return this.s;
	}

	public PrintWriter initSockWriter(Socket connexion) {

		try {
			PrintWriter p = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connexion.getOutputStream())),
					true);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public BufferedReader initSockReader(Socket connexion) {
		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			return b;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

	}

	public String sendRequest(String msg, PrintWriter w) {
		w.println(msg);
		w.flush();
		return msg;
	}

	public String getAnswer(BufferedReader b) throws IOException {
		try {

			return b.readLine();

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	public String recupereNomFichier(String a) {
		String c = "";

		for (int i = a.length() - 1; i > 0; i--) {
			if (a.charAt(i) == '\\') {
				c = a.substring(i + 1);
				return c;
			}
		}
		return c;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public void setThread(BufferedReader br, PrintWriter pw, TextArea t, TextArea l) throws IOException {
		this.thread = new ThreadClient(br, pw, t, l);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public long envoieTailleFichier(String chemin) {
		File monFichier = new File(chemin);

		return monFichier.length();
	}

	public byte[] remplirTableauByte(String chemin) throws IOException {
		FileInputStream f = new FileInputStream(new File(chemin));
		byte[] b = new byte[(int) this.envoieTailleFichier(chemin)];
		int i = 0;
		int val;
		val = f.read();

		do {

			b[i] = (byte) val;
			val = f.read();
			i++;
		} while (val != -1);

		return b;

	}

	public void envoieTableau(PrintWriter p, byte[] b) {
		for (int i = 0; i < b.length; i++) {
			p.println(b[i]);
			p.flush();
		}
	}

	public void lire(byte[] b) {
		System.out.println(b);
		String s = new String(b, StandardCharsets.UTF_8);

		System.out.println(s);

	}
	public void deconnexion() throws IOException {
		this.s.close();
		this.thread.getInClient1().close();
		this.thread.getOutClient1().close();
		this.thread.arret();
	}

	public void envoieInfoFichier(String chemin, BufferedReader b, PrintWriter p) throws IOException {

	
		this.sendRequest(this.recupereNomFichier(chemin), p);

		this.sendRequest(Long.toString(this.envoieTailleFichier(chemin)), p);
		

	}
}