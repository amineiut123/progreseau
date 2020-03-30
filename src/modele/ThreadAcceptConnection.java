package modele;

public class ThreadAcceptConnection implements Runnable {

	private Serveur serveur;

	public ThreadAcceptConnection(Serveur s) {
		this.serveur = s;
		new Thread(this).start();
	}

	@Override
	public void run() {

		while (true) {
			this.serveur.accepterConnexion();
			
		}
	}

}
