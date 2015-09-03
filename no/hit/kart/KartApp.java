package no.hit.kart;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	public static void main(String[] args)
	{
		launch(args);
		System.exit(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Løst kopiert fra mal leksjon 1
		Group root = new Group();
		Scene scene = new Scene(root, 800, 400); // Størrelsen på bildet - verden.jpg

		primaryStage.setScene(scene);
		primaryStage.setTitle("KartApp");

		// Last bildet
		ImageView iv = lastImageViewFraFil("img/verden.jpg");
		if (iv != null) // Sjekk at bildet ble lastet
			root.getChildren().add(iv);
		primaryStage.setResizable(false);

		primaryStage.show();

	}

	private ArrayList<Hendelse> lastHendelserFraFil(String filbane)
	{

		ArrayList<Hendelse> hendelser = new ArrayList<Hendelse>();
		BufferedReader innfil;
		// Blokk for lasting av fil
		try {
			InputStream in = getClass().getResourceAsStream("data/hendelser.txt");
			innfil = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		} catch (Exception e) {
			System.err.println("[lastHendelserFrafil] Feil under lasting av tekstfil");
		}
		// Blokk for lesing av data.
		try {
			String tempLinje = innfil.readLine();
			// Hvis den leste linjen er tom avbryt løkka. Dersom filen som blir lest inn allerede er tom
			// Utføres aldri instruksjonene i løkka
			while (tempLinje != null) {

			}
			innfil.close(); // Lukk leseren
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
