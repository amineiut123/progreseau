package controleur;

import modele.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Contreseau implements Initializable {

	@FXML
	private BorderPane border;

	@FXML
	private Button Quitter;

	@FXML
	private Button Fichier;

	@FXML
	private AnchorPane anchor;

	@FXML
	private Button Client;

	@FXML
	private Button Host;

	@FXML
	private TextField textePort;

	@FXML
	private TextField texteIP;

	@FXML
	private Label labelChoix;

	@FXML
	private Label saisieIP;

	@FXML
	private Label saisieNom;

	@FXML
	private TextField texteNom;

	@FXML
	private Label saisiePort;

	@FXML
	private Button deconnecterButton;

	@FXML
	private AnchorPane anchor2;

	@FXML
	private TextField texteMessage;

	@FXML
	private TextArea AreaMessage;

	@FXML
	private Label LabelDiscussion;

	@FXML
	private Button OKButton;

	@FXML
	private TextArea logArea;

	@FXML
	private Label logLabel;

	private TextArea cheminFichier;

	private Client client;
	private Serveur serveur;
	private BufferedReader buffClient;
	private PrintWriter printClient;
	private ThreadAcceptConnection t;

	@FXML
	void ConnectClient(ActionEvent event) throws NumberFormatException, UnknownHostException, IOException {
		this.logArea.clear();
		
		this.client = new Client(this.texteIP.getText(), Integer.parseInt(this.textePort.getText()),
				this.texteNom.getText());

		this.buffClient = this.client.initSockReader(this.client.getSocket());
		this.printClient = this.client.initSockWriter(this.client.getSocket());

		this.client.setThread(this.buffClient, this.printClient, this.AreaMessage, this.logArea);
		this.client.sendRequest(this.client.getNom(), this.printClient);

		this.border.getChildren().remove(this.anchor);
		this.anchor.setOpacity(0);
		this.anchor2.setOpacity(1);
		this.Fichier.setOpacity(1);


	}

	@FXML
	void FileChooser(ActionEvent event)  {
		FileChooser f = new FileChooser();
		f.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"), new ExtensionFilter("All Files", "*.*"));
		File fichier = f.showOpenDialog(null);
		if (f != null) {
			this.logArea.setText(this.logArea.getText() + "\n" + fichier.getAbsolutePath());
			this.client.sendRequest("!fichier", this.printClient);

			try {
				this.client.envoieInfoFichier(fichier.getAbsolutePath(), this.buffClient, this.printClient);
				byte[] b;
				b = this.client.remplirTableauByte(fichier.getAbsolutePath());
				this.client.envoieTableau(this.printClient, b);
				this.logArea.setText(this.logArea.getText() + "\n Envoie du fichier ");
			
			} catch (IOException e) {
			
				
				this.logArea.setText(this.logArea.getText() + "\n Erreur lors de la selection du fichier ");
			}

		} 
			
	}

	@FXML
	void ConnectHost(ActionEvent event) throws NumberFormatException, IOException {
		this.logArea.clear();

		this.serveur = new Serveur(this.texteNom.getText(), Integer.parseInt(this.textePort.getText()));
		this.t = new ThreadAcceptConnection(this.serveur);

		this.client = new Client(this.texteIP.getText(), Integer.parseInt(this.textePort.getText()),
				this.texteNom.getText());

		this.buffClient = this.client.initSockReader(this.client.getSocket());
		this.printClient = this.client.initSockWriter(this.client.getSocket());
		this.client.setThread(this.buffClient, this.printClient, this.AreaMessage, this.logArea);

		this.client.sendRequest(this.client.getNom(), this.printClient);
		this.border.getChildren().remove(this.anchor);
		this.anchor.setOpacity(0);
		this.anchor2.setOpacity(1);
		this.Fichier.setOpacity(1);
		
		this.texteIP.clear();
		this.texteNom.clear();
		this.textePort.clear();
	}

	@FXML
	void QuitterButton(ActionEvent event) throws IOException {
		System.exit(0);

	}

	@FXML
	void SeDeconnecter(ActionEvent event) throws IOException {

		this.client.deconnexion();
		this.logArea.setText("Deconnexion");
		this.border.getChildren().add(this.anchor);
		this.anchor.setOpacity(1);
		this.anchor2.setOpacity(0);

	}

	@FXML
	void ButtonOK(ActionEvent event) throws IOException {
		this.client.sendRequest(this.texteMessage.getText(), this.printClient);
		this.AreaMessage.setText(this.AreaMessage.getText() + "\n" + this.texteMessage.getText());

		
		this.texteMessage.clear();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.anchor2.setOpacity(0);
		this.Fichier.setOpacity(0);

	}

}
