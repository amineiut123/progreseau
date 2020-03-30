package modele;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {

	private String nom;
	private ServerSocket socketServeur;
	private ClientCoteServeur[] listeClient;
	private ThreadAcceptConnection tConnect;


	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the socketServeur
	 */
	public ServerSocket getSocketServeur() {
		return socketServeur;
	}

	/**
	 * @param socketServeur
	 *            the socketServeur to set
	 */
	public void setSocketServeur(ServerSocket socketServeur) {
		this.socketServeur = socketServeur;
	}

	/**
	 * @return the listeClient
	 */
	public ClientCoteServeur[] getListeClient() {
		return listeClient;
	}

	/**
	 * @param listeClient
	 *            the listeClient to set
	 */
	public void setListeClient(ClientCoteServeur[] listeClient) {
		this.listeClient = listeClient;
	}

	/**
	 * @return the threadServeurC1
	 */
	public ThreadServeur getThreadServeurC1() {
		return threadServeurC1;
	}

	/**
	 * @param threadServeurC1
	 *            the threadServeurC1 to set
	 */
	public void setThreadServeurC1(ThreadServeur threadServeurC1) {
		this.threadServeurC1 = threadServeurC1;
	}

	/**
	 * @return the threadServeurC2
	 */
	public ThreadServeur getThreadServeurC2() {
		return threadServeurC2;
	}

	/**
	 * @param threadServeurC2
	 *            the threadServeurC2 to set
	 */
	public void setThreadServeurC2(ThreadServeur threadServeurC2) {
		this.threadServeurC2 = threadServeurC2;
	}

	private ThreadServeur threadServeurC1;
	private ThreadServeur threadServeurC2;

	public Serveur(String nom, int port) throws IOException {
		this.nom = nom;
		this.listeClient = new ClientCoteServeur[2];
		this.socketServeur = new ServerSocket(port);
		System.out.println("Serveur crée");


	}

	public void accepterConnexion() {

		try {
			if (this.listeClient[0] == null) {
				Socket client = this.socketServeur.accept();
				ClientCoteServeur client1 = new ClientCoteServeur(client,
						new BufferedReader(new InputStreamReader(client.getInputStream())),
						new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true));
				String nom = client1.getIn().readLine();
				client1.setNom(nom);
				listeClient[0] = client1;
				System.out.println("connexion de " + client1);
		

			} else {
				Socket client = this.socketServeur.accept();
				ClientCoteServeur client2 = new ClientCoteServeur(client,
						new BufferedReader(new InputStreamReader(client.getInputStream())),
						new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true));
				String nom = client2.getIn().readLine();
				client2.setNom(nom);
				listeClient[1] = client2;
				System.out.println("connexion de " + client2);
			
				System.out.println("creation de threads");
				this.threadServeurC1 = new ThreadServeur(this.listeClient[0].getIn(), this.listeClient[1].getOut());
				this.threadServeurC2 = new ThreadServeur(this.listeClient[1].getIn(), this.listeClient[0].getOut());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
