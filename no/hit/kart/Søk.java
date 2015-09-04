package no.hit.kart;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by Jo Øivind Gjernes on 04.09.2015.
 * <p>
 * Klasse der jeg samler metoder som handler om søk
 */
public class Søk
{
	public static Hendelse søk(Punkt søkePunkt, ArrayList<Hendelse> hendelser)
	{
		Hendelse nærmest;
		int antallHendelser = hendelser.size();
		ArrayList<Double> avstander = new ArrayList<Double>();

		// Primitiv "feilsjekking"
		if (antallHendelser == 0)
			return null;
		if (søkePunkt == null)
			return null;

		for (Hendelse h : hendelser) {
			avstander.add(søkePunkt.avstand(h.getPunkt()));
		}

		nærmest = hendelser.get(minIndex(avstander)); // Finner hendelsen som korresponderer til den minste avstanden.

		return nærmest;
	}

	private static int minIndex(ArrayList<Double> liste)
	{
		return liste.indexOf(Collections.min(liste));
	}

	// Metode for å håndtere søkedialog, returnerer punktet brukeren søkte på
	// Sjekker ikke for gyldige søkeverdier.
	public static Punkt søkeDialog()
	{
		TextInputDialog dialog = new TextInputDialog();

		dialog.setHeaderText("Søk etter hendelse");
		dialog.setContentText("Søk etter en hendelse ved å skrive inn koordinater på formatet: x,y\n " +
			"Bruk kun heltall og komma i svaret");

		boolean fåttSvar = false;
		Punkt søkePunkt = new Punkt(-1, -1); // Dummy verdi. Kan sjekkes for evt. feil!

		while (!fåttSvar) {
			Optional<String> dialogResultat = dialog.showAndWait();
			if (dialogResultat.isPresent()) {
				String[] tolketResultat;
				try {
					tolketResultat = dialogResultat.get().split(",");
					if (tolketResultat.length != 2) {
						prøvPåNytt(dialogResultat.get());
						continue; // Prøv på nytt!
					}
					int x = Integer.parseInt(tolketResultat[0]);
					int y = Integer.parseInt(tolketResultat[1]);
					søkePunkt = new Punkt(x, y);
					fåttSvar = true;

				} catch (Exception e) {
					prøvPåNytt("Ugyldig");
				}
			} else {
				prøvPåNytt("Ugyldig");
			}

		}

		return søkePunkt; // Sender svar. Forhåpentligvis gyldig.
	}

	private static void prøvPåNytt(String res)
	{
		Alert feilISvar = new Alert(Alert.AlertType.ERROR);
		feilISvar.setTitle("Feil!");
		feilISvar.setContentText("Søkesetningen du skrev inn kunne ikke tolkes!\n" +
			"Du skrev: " + res);
		feilISvar.showAndWait();
	}
}
