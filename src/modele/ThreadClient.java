package modele;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.omg.CORBA.Request;

import javafx.scene.control.TextArea;

public class ThreadClient implements Runnable {

	private BufferedReader inClient1;
	private PrintWriter outClient1;
	private TextArea affichage;
	private TextArea logs;

	private boolean oui;

	public ThreadClient(BufferedReader buffClient1, PrintWriter printClient1, TextArea t, TextArea l) throws IOException {
		this.inClient1 = buffClient1;
		this.outClient1 = printClient1;
		this.oui = true;
		this.affichage = t;
		this.logs = l;
		new Thread(this).start();
	}

	@Override
	public void run() {

		String recu1 = "";
		String nomFichier;
		int tailleFichier;
		Scanner sc = new Scanner(System.in);

		while (!recu1.equals("!EXIT") && oui) {
			try {

				recu1 = inClient1.readLine();

				if (recu1.equals("!fichier")) {
					this.logs.setText(this.logs.getText() + "\n Reception d'un fichier");
					nomFichier = inClient1.readLine();
					tailleFichier = Integer.parseInt(inClient1.readLine());
					 
					
					this.logs.setText(this.logs.getText() + "\n Nom fichier : " + nomFichier + "\n Taille : " + tailleFichier);

					FileOutputStream lienFichier = new FileOutputStream(
							new File(System.getProperty("user.home") + "\\Desktop\\" + nomFichier));
					
					byte[] ContenuFichier = new byte[tailleFichier];

					byte byteRecu;

					int compteur = 0;

					while (compteur < tailleFichier) {

						byteRecu = Byte.valueOf(inClient1.readLine());
						ContenuFichier[compteur] = byteRecu;

						compteur++;

				
					}
					lienFichier.write(ContenuFichier);
					recu1 = "";
				}
				if (recu1 != null) {
					this.affichage.setText(this.affichage.getText()  + "\n"  + recu1);
				}

			} catch (IOException ex) {
				this.arret();
			}

		}
	}

	public void arret() {
		this.oui = false;
	}

	/**
	 * @return the inClient1
	 */
	public BufferedReader getInClient1() {
		return inClient1;
	}

	/**
	 * @param inClient1 the inClient1 to set
	 */
	public void setInClient1(BufferedReader inClient1) {
		this.inClient1 = inClient1;
	}

	/**
	 * @return the outClient1
	 */
	public PrintWriter getOutClient1() {
		return outClient1;
	}

	/**
	 * @param outClient1 the outClient1 to set
	 */
	public void setOutClient1(PrintWriter outClient1) {
		this.outClient1 = outClient1;
	}

}