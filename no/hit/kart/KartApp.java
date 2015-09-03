package no.hit.kart;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.xml.transform.sax.SAXSource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jo on 03.09.2015.
 * <p>
 * Innlevering 1 - Leksjon 3
 * <p>
 * Skrevet i IntelliJ IDEA
 */

public class KartApp extends Application
{
	List<Hendelse> hendelseList;
	List<Circle> sirkelListe;
	public static void main(String[] args)
	{
		launch(args);
		System.exit(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Løst kopiert fra mal leksjon 1
		hendelseList = lastHendelserFraFil("KartApp/data/hendelser.txt");
		//hendelseList = new ArrayList<Hendelse>();
		printHendelser();
		Group root = new Group();
		Scene scene = new Scene(root, 800, 400); // Størrelsen på bildet - verden.jpg

		primaryStage.setScene(scene);
		primaryStage.setTitle("KartApp");

		// Last bildet
		ImageView iv = lastImageViewFraFil("img/verden.jpg");
		if (iv != null) // Sjekk at bildet ble lastet
			root.getChildren().add(iv);
		primaryStage.setResizable(false);

		if (iv != null) {
			iv.setOnMouseClicked((MouseEvent e) -> {
				int x = (int) e.getX();
				int y = (int) e.getY();

				sirkelListe.add(new Circle(x, y, 15, Color.GREEN));
				fjernSirkler(root);
				tegnSirkler(root);
			});
			iv.setOnScroll((ScrollEvent e) -> {
				fjernSirkler(root);
				opprettSirkler();
				tegnSirkler(root);
			});
		}
		// Dummy søke aktivasjon - med knappen S / s
		scene.setOnKeyPressed((KeyEvent e) -> {
			if (e.getCode().equals(KeyCode.S)) {
				System.out.println("Søke!");
			}
		});
		opprettSirkler(); // Tegne sirkler OVER bildet.
		tegnSirkler(root);
		primaryStage.show();

	}

	private void fjernSirkler(Group root)
	{
		for (Circle c : sirkelListe) {
			root.getChildren().remove(c);
		}
	}

	private void printHendelser()
	{
		for (Hendelse h : hendelseList) {
			System.out.println(h.toString());
		}
	}

	/*
	Send med en referanse til "root"
	 */
	private void opprettSirkler()
	{
		sirkelListe = new ArrayList<Circle>();
		for (Hendelse h : hendelseList) { // Ranged for-løkke. Hvis hendelsesliste er tom blir ikke noe gjort!

			// lager midlertidige variabler for å få mer lettlest kode.
			int x = h.getPunkt().getX();
			int y = h.getPunkt().getY();
			sirkelListe.add(new Circle(x, y, 5, Color.rgb(0, 0, 255)));
		}
	}

	private void tegnSirkler(Group root)
	{
		int teller = 0;
		for (Circle c : sirkelListe) {
			// Legger til relevant tooltip ved mouse-over
			if (teller < hendelseList.size()) {
				Tooltip.install(c, new Tooltip(hendelseList.get(teller++).getHendelsesTekst()));
			}

			// Sirkelen endrer farge når man "hovrer" over den
			c.setOnMouseEntered((MouseEvent e) -> {
				c.setFill(Color.RED);
			});
			// Skifter farge tilbake til originalfargen hvis man går ut.
			c.setOnMouseExited(e -> c.setFill(Color.rgb(0, 0, 255)));
			root.getChildren().add(c);
		}
	}


	private ArrayList<Hendelse> lastHendelserFraFil(String filbane)
	{

		ArrayList<Hendelse> hendelser = new ArrayList<Hendelse>();
		BufferedReader innfil;
		// Blokk for lasting av fil
		try {
			FileInputStream fi = new FileInputStream(filbane);
			InputStreamReader ir = new InputStreamReader(fi, "UTF-8");
			// Skjønte ikke hvordan metoden som ble foreslått fungerte - så lagde inputstreamen "manuelt"
			innfil = new BufferedReader(ir);
		} catch (Exception e) {
			// fikk annen mappestruktur med den anbefalte metoden
			System.err.println("[lastHendelserFrafil] Feil under lasting av tekstfil\n" + e.getMessage());
			return hendelser; // Returnerer tom liste - kan ikke fortsette lesing.
		}
		// Blokk for lesing av data.
		try {
			String tempLinje = innfil.readLine();
			// Hvis den leste linjen er tom avbryt løkka. Dersom filen som blir lest inn allerede er tom
			// Utføres aldri instruksjonene i løkka
			while (tempLinje != null) {
				String[] str = tempLinje.split(";");
				if (str.length == 4) { // Sjekker om om strengen er firedelt. (dobbeltarbeid - burde laget en konstruktør som tok String[]
					hendelser.add(new Hendelse(tempLinje));
				}
				tempLinje = innfil.readLine();
			}
			innfil.close(); // Lukk leseren
		} catch (Exception e) {
			System.err.println("[lastHendelserFrafil] Feil under parsing av strenger fra fil\n" + e.getMessage());
		}
		return hendelser;
	}
	private ImageView lastImageViewFraFil(String filbane)
	{
		Image bilde; // Deklareres før skopet til try-catch blokken for å "eksistere" etter try-catchen
		try {
			bilde = new Image(filbane);
		} catch (Exception e) { // fanger alt!
			System.err.println("Feil: " + e.getMessage()); // midlertidig feilmelding
			System.err.println("Tar utgangspunkt i denne mappen: " +
				System.getProperty("user.dir") + " og leter etter: " + filbane); // Litt mer diagnostikk.
			return null;
		}

		ImageView bildenode = new ImageView();
		bildenode.setImage(bilde);

		return bildenode;
	}
}
