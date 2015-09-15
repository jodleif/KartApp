package no.hit.kart;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes on 03.09.2015.
 * <p>
 * Innlevering 1
 * <p>
 * Skrevet i IntelliJ IDEA - importert i netbeans og testet(ikke enda)
 *
 * Skal ha laget løsninger på alt som ble spurt om i oppgaven.
 * Har gjort noen små tilleg:
 * - Hvis man "hovrer" med musepekeren over et punkt vises det et
 * tooltip som angir hendelsesteksten til de ulike hendelsene.
 * - Søking utførses ved at man trykker knappen "s" på tastaturet.
 * - Har valgt å bruke "lambda-funksjoner" for de ulike "eventhandlerene" i koden. Dette tok jeg fra eksempler i
 * læreboken
 *
 * Problemer med løsningen:
 * - Lite elegant å sende med "root" til alle metodene som tegner objekter. Burde laget en privat variabel som holder på
 * "root" slik at jeg ikke trenger å sende den som parameter for de ulike funskjoenene i denne klassen.
 * - start metoden er kanskje litt for stor?! burde delt den inn i fler undermetoder.
 * - Ganske ekstensiv feilsjekking, men muligens noe ustrukturert. Har prøvd å dokumentere i de ulike feilmeldingene
 * til konsollen hvor eventuelle problemer har oppstått. (med navn på klasser, metoder)
 * - Fjerner alle sirklene for dersom en har blitt endret, og tegner alle på nytt. Unødvendig?!
 */

public class KartApp extends Application
{
	ArrayList<Hendelse> hendelseList;
	ArrayList<Circle> sirkelListe;
	Text text; // tekst å vise på skjerm

	public static void main(String[] args)
	{

		launch(args);
		System.exit(0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Løst kopiert fra mal leksjon 1
		// denne metoden så en annen mappestruktur enn hva jeg trengte for å laste bildet (merkelig)
		hendelseList = lastHendelserFraFil("KartApp/data/hendelser.txt");

		text = new Text();

		//hendelseList = new ArrayList<Hendelse>();

		//printHendelser(); teste at metodene ble lastet riktig inn. Skrives i konsollet.

		Group root = new Group();
		Scene scene = new Scene(root, 800, 400); // Størrelsen på bildet - verden.jpg

		primaryStage.setScene(scene);
		primaryStage.setTitle("KartApp");

		// Last bildet
		ImageView iv = lastImageViewFraFil("img/verden.jpg");
		if (iv != null) // Sjekk at bildet ble lastet
			root.getChildren().add(iv);
		primaryStage.setResizable(false);

		tegnRektangelForTekst(root);

		/*
		if (iv != null) {
			iv.setOnMouseClicked((MouseEvent e) -> {
				int x = (int) e.getX();
				int y = (int) e.getY();

				sirkelListe.add(new Circle(x, y, 15, Color.GREEN));
				fjernSirkler(root);
				tegnSirkler(root);
			});
		}*/
		// Søke aktivasjon - med knappen S / s
		scene.setOnKeyPressed((KeyEvent e) -> {
			if (e.getCode().equals(KeyCode.S)) {
				Punkt søkePunkt = Søk.søkeDialog(); // Åpner en dialog som spør brukeren om informasjon til å lage et søkepunkt
				tegnKryssIPunkt(søkePunkt, root); // Tegner et svart kryss i punktet brukeren søkte.
				Hendelse h = Søk.søk(søkePunkt, hendelseList); // Søker etter nærmeste hendelse til punktet brukeren spesifiserte

				// Sjekker om søkepunktet er innenfor en rimlig avstand
				if (søkePunkt.avstand(h.getPunkt()) <= 20.0d) { // Dersom punktet er innenfor en avstand på 20 piksler flagger man hendelsen slik at den tegnes grønn
					h.setFunnetISøk(true);
					opprettSirkler();
					tegnSirkler(root);
					skrivTekstIRektangel(h.getHendelsesTekst(), root);
				}

			}
		});

		opprettSirkler(); // Tegne sirkler OVER bildet.
		tegnSirkler(root);// derfor er dette metodekallet etter bildet blir lagt til.

		primaryStage.show(); // Vis det genererte GUI-et

	}

	// Fjerne sirklene - brukes dersom noen har endret farge. (de må lages på nytt)
	private void fjernSirkler(Group root)
	{
		for (Circle c : sirkelListe) {
			root.getChildren().remove(c);
		}
	}

	// Metode for testing at hendelsene ble lastet riktig inn.
	private void printHendelser()
	{
		for (Hendelse h : hendelseList) {
			System.out.println(h.toString());
		}
	}

	// Metode for å tegne et kryss i et punkt.
	private void tegnKryssIPunkt(Punkt p, Group root)
	{
		Line l1 = new Line(p.getX() - 2, p.getY() - 2, p.getX() + 2, p.getY() + 2);
		Line l2 = new Line(p.getX() + 2, p.getY() - 2, p.getX() - 2, p.getY() + 2);
		root.getChildren().add(l1);
		root.getChildren().add(l2);
	}

	/*
	Opretter en liste med sirkler, som skal tegnes oppå kartet.
	Dersom en hendelse er flagget som funnet, tegnes sirklen litt større og rød
	 */
	private void opprettSirkler()
	{
		sirkelListe = new ArrayList<Circle>();
		for (Hendelse h : hendelseList) { // Ranged for-løkke. Hvis hendelsesliste er tom blir ikke noe gjort!

			// lager midlertidige variabler for å få mer lettlest kode.
			int x = h.getPunkt().getX();
			int y = h.getPunkt().getY();

			// Kode for å sjekke om hendelsen er "funnet" i et søk
			if (!h.getFunnetISøk()) {
				sirkelListe.add(new Circle(x, y, 5, Color.rgb(0, 0, 255)));
			} else {
				// Grønn for funnet!
				sirkelListe.add(new Circle(x, y, 6, Color.GREEN));
			}
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

	private void tegnRektangelForTekst(Group root)
	{
		Rectangle rect = new Rectangle(650, 360, 125, 30);

		// Litt avrundede kanter
		rect.setArcHeight(5);
		rect.setArcWidth(5);
		rect.setFill(Color.rgb(0, 0, 0, 0.2));
		root.getChildren().add(new Text(655, 355, "Søkeresultat"));
		root.getChildren().add(rect);
	}

	private void skrivTekstIRektangel(String tekst, Group root)
	{
		root.getChildren().remove(text); // fjern tekst, uansett. (trengs hvis det er skrevet noe fra før)
		text = new Text(660, 379, tekst);
		root.getChildren().add(text);
	}
}
